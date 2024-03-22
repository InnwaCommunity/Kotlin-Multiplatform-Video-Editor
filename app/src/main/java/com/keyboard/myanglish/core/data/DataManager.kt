package com.keyboard.myanglish.core.data

import android.os.Build
import com.keyboard.myanglish.BuildConfig
import com.keyboard.myanglish.utils.appContext
import timber.log.Timber
import java.io.File
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

object DataManager {

    private val lock = ReentrantLock()

    var synced = false
        private set

    // If Android version supports direct boot, we put the hierarchy in device encrypted storage
    // instead of credential encrypted storage so that data can be accessed before user unlock
    val dataDir: File = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Timber.d("Using device protected storage")
        appContext.createDeviceProtectedStorageContext().dataDir
    } else {
        File(appContext.applicationInfo.dataDir)
    }

    fun sync() = lock.withLock {
        synced = false
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