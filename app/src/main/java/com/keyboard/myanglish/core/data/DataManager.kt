package com.keyboard.myanglish.core.data

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.os.Build
import com.keyboard.myanglish.BuildConfig
import com.keyboard.myanglish.utils.appContext
import com.keyboard.myanglish.utils.isJavaIdentifier
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.xmlpull.v1.XmlPullParser
import timber.log.Timber
import java.io.File
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

/**
 * Build up a Filesystem hierarchy at [dataDir]
 *
 * Operations are synchronized
 */
object DataManager {

    const val PLUGIN_INTENT = "${BuildConfig.APPLICATION_ID}.plugin.MANIFEST"

    private val lock = ReentrantLock()

    private val json by lazy { Json { prettyPrint = true } }

    var synced = false
        private set

    // should be consistent with the deserialization in DataDescriptorPlugin (:build-logic)
    private fun deserializeDataDescriptor(raw: String): DataDescriptor {
        return json.decodeFromString<DataDescriptor>(raw)
    }

    private fun serializeDataDescriptor(descriptor: DataDescriptor): String {
        return json.encodeToString(descriptor)
    }

    // If Android version supports direct boot, we put the hierarchy in device encrypted storage
    // instead of credential encrypted storage so that data can be accessed before user unlock
    val dataDir: File = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Timber.d("Using device protected storage")
        appContext.createDeviceProtectedStorageContext().dataDir
    } else {
        File(appContext.applicationInfo.dataDir)
    }

    private val loadedPlugins = mutableSetOf<PluginDescriptor>()
    private val failedPlugins = mutableMapOf<String, PluginLoadFailed>()

    fun getLoadedPlugins(): Set<PluginDescriptor> = loadedPlugins
    fun getFailedPlugins(): Map<String, PluginLoadFailed> = failedPlugins

    /**
     * Will be cleared after each sync
     */
    private val callbacks = mutableListOf<() -> Unit>()

    fun addOnNextSyncedCallback(block: () -> Unit) =
        callbacks.add(block)

    fun detectPlugins(): Pair<Set<PluginDescriptor>, Map<String, PluginLoadFailed>> {
        val toLoad = mutableSetOf<PluginDescriptor>()
        val preloadFailed = mutableMapOf<String, PluginLoadFailed>()

        val pm = appContext.packageManager

        val pluginPackages = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            pm.queryIntentActivities(
                Intent(PLUGIN_INTENT),
                PackageManager.ResolveInfoFlags.of(PackageManager.MATCH_ALL.toLong())
            )
        } else {
            pm.queryIntentActivities(Intent(PLUGIN_INTENT), PackageManager.MATCH_ALL)
        }.map {
            it.activityInfo.packageName
        }

        Timber.d("Detected plugin packages: ${pluginPackages.joinToString()}")

        // Parse plugin.xml
        for (packageName in pluginPackages) {
            val res = pm.getResourcesForApplication(packageName)

            @SuppressLint("DiscouragedApi")
            val resId = res.getIdentifier("plugin", "xml", packageName)
            if (resId == 0) {
                Timber.w("Failed to get the plugin descriptor of $packageName")
                failedPlugins[packageName] = PluginLoadFailed.MissingPluginDescriptor
                continue
            }
            val parser = res.getXml(resId)
            var eventType = parser.eventType
            var domain: String? = null
            var apiVersion: String? = null
            var description: String? = null
            var hasService = false
            val libraryDependency = mutableMapOf<String, List<String>>()
            var library: String? = null
            var dependency: ArrayList<String>? = null
            var text: String? = null
            while ((eventType != XmlPullParser.END_DOCUMENT)) {
                when (eventType) {
                    XmlPullParser.TEXT -> text = parser.text
                    XmlPullParser.START_TAG -> when (parser.name) {
                        "library" -> {
                            dependency = arrayListOf()
                            for (i in 0..<parser.attributeCount) {
                                if (parser.getAttributeName(i) == "name") {
                                    library = parser.getAttributeValue(i)
                                }
                            }
                        }
                    }
                    XmlPullParser.END_TAG -> when (parser.name) {
                        "apiVersion" -> apiVersion = text
                        "domain" -> domain = text
                        "description" -> description = text
                        "hasService" -> hasService = text?.lowercase() == "true"
                        "dependency" -> dependency?.add(text!!)
                        "library" -> {
                            if (library != null && dependency != null) {
                                libraryDependency[library] = dependency
                                library = null
                                dependency = null
                            }
                        }
                    }
                }
                eventType = parser.next()
            }
            parser.close()

            if (description?.startsWith("@string/") == true) {
                // Replace "@string/" with string resource
                val s = description.substring(8)
                if (s.isJavaIdentifier()) {
                    @SuppressLint("DiscouragedApi")
                    val id = res.getIdentifier(s, "string", packageName)
                    if (id != 0) description = res.getString(id)
                }
            }

            if (apiVersion != null && description != null) {
                if (PluginDescriptor.pluginAPI == apiVersion) {
                    val info = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        pm.getPackageInfo(
                            packageName,
                            PackageManager.PackageInfoFlags.of(PackageManager.GET_META_DATA.toLong())
                        )
                    } else {
                        pm.getPackageInfo(packageName, PackageManager.GET_META_DATA)
                    }
                    toLoad.add(
                        PluginDescriptor(
                            packageName,
                            apiVersion,
                            domain,
                            description,
                            hasService,
                            info.versionName,
                            info.applicationInfo.nativeLibraryDir,
                            libraryDependency
                        )
                    )
                } else {
                    Timber.w("$packageName's api version [$apiVersion] doesn't match with the current [${PluginDescriptor.pluginAPI}]")
                    preloadFailed[packageName] = PluginLoadFailed.PluginAPIIncompatible(apiVersion)
                }
            } else {
                Timber.w("Failed to parse plugin descriptor of $packageName")
                preloadFailed[packageName] = PluginLoadFailed.PluginDescriptorParseError
            }
        }
        return toLoad to preloadFailed
    }

    fun sync() = lock.withLock {

    }

    fun deleteAndSync() {
        lock.withLock {
            dataDir.resolve("descriptor.json").delete()
            dataDir.resolve("README.md").delete()
            dataDir.resolve("usr").deleteRecursively()
        }
        sync()
    }
}