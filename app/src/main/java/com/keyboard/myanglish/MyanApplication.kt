package com.keyboard.myanglish

import android.annotation.SuppressLint
import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Process
import android.util.Log
import androidx.preference.PreferenceManager
import com.keyboard.myanglish.daemon.MyanDaemon
import com.keyboard.myanglish.data.clipboard.ClipboardManager
import com.keyboard.myanglish.data.prefs.AppPrefs
import com.keyboard.myanglish.data.theme.ThemeManager
import com.keyboard.myanglish.utils.AppUtil
import com.keyboard.myanglish.utils.startActivity
import com.keyboard.myanglish.utils.userManager
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.plus
import timber.log.Timber
import java.lang.IllegalStateException
import kotlin.system.exitProcess

class MyanApplication : Application() {

    val coroutineScope = MainScope() + CoroutineName("MyanApplication")

    private val shutdownReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action != Intent.ACTION_SHUTDOWN) return
            Timber.d("Device shutting down, trying to save fcitx state...")
            val myan = MyanDaemon.getFirstConnectionOrNull()
                ?: return Timber.d("No active fcitx connection, skipping")
            myan.runImmediately { save() }
        }
    }

    private val unlockReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            if (intent.action != Intent.ACTION_USER_UNLOCKED) return
            if (!isDirectBootMode) return
            Timber.d("Device unlocked, app will exit now and restart to normal mode")
            MyanDaemon.getFirstConnectionOrNull()?.also {
                MyanDaemon.stopMyan()
            }
            AppUtil.exit()
        }
    }

    var isDirectBootMode = false
        private set

    val directBootAwareContext: Context
        @SuppressLint("NewApi")
        get() = if (isDirectBootMode) createDeviceProtectedStorageContext() else applicationContext

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && !userManager.isUserUnlocked) {
            isDirectBootMode = true
            registerReceiver(unlockReceiver, IntentFilter(Intent.ACTION_USER_UNLOCKED))
        }
        val ctx = directBootAwareContext

//        if (!BuildConfig.DEBUG) {
//            Thread.setDefaultUncaughtExceptionHandler { _, e ->
//                startActivity<LogActivity> {
//                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                    putExtra(LogActivity.FROM_CRASH, true)
//                    // avoid transaction overflow
//                    val truncated = e.stackTraceToString().let {
//                        if (it.length > MAX_STACKTRACE_SIZE)
//                            it.take(MAX_STACKTRACE_SIZE) + "<truncated>"
//                        else
//                            it
//                    }
//                    putExtra(LogActivity.CRASH_STACK_TRACE, truncated)
//                }
//                exitProcess(10)
//            }
//        }

        instance = this
        // we don't have AppPrefs available yet
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(ctx)
        if (BuildConfig.DEBUG || sharedPrefs.getBoolean("verbose_log", false)) {
            Timber.plant(object : Timber.DebugTree() {
                override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                    super.log(priority, "[${Thread.currentThread().name}] $tag", message, t)
                }
            })
        } else {
            Timber.plant(object : Timber.Tree() {
                override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                    if (priority < Log.INFO) return
                    Log.println(priority, "[${Thread.currentThread().name}]", message)
                }
            })
        }

        Timber.d("isDirectBootMode=$isDirectBootMode")

        AppPrefs.init(sharedPrefs)
        // record last pid for crash logs
        AppPrefs.getInstance().internal.pid.apply {
            val currentPid = Process.myPid()
            lastPid = getValue()
            Timber.d("Last pid is $lastPid. Set it to current pid: $currentPid")
            setValue(currentPid)
        }
        ClipboardManager.init(ctx)
        ThemeManager.init(resources.configuration)
//        Locales.onLocaleChange(resources.configuration)
        registerReceiver(shutdownReceiver, IntentFilter(Intent.ACTION_SHUTDOWN))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && !isDirectBootMode) {
            AppPrefs.getInstance().syncToDeviceEncryptedStorage()
            ThemeManager.syncToDeviceEncryptedStorage()
        }
    }

    companion object {
        private var lastPid: Int? = null
        private var instance: MyanApplication? = null
        fun getInstance() =
            instance ?: throw IllegalStateException("MyanApplication has not been created")

        fun getLastPid() = lastPid
        private const val MAX_STACKTRACE_SIZE = 128000
    }
}