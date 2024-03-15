package com.keyboard.myanglish.utils

import android.content.ComponentName
import android.os.Build
import android.provider.Settings
import com.keyboard.myanglish.BuildConfig
import com.keyboard.myanglish.MyanInputMethodService

object InputMethodUtil {

    @JvmField
    val serviceName: String = MyanInputMethodService::class.java.name

    @JvmField
    val componentName: String =
        ComponentName(appContext, MyanInputMethodService::class.java).flattenToShortString()

    fun isEnabled(): Boolean {
        return appContext.inputMethodManager.enabledInputMethodList.any {
            it.packageName == BuildConfig.APPLICATION_ID && it.serviceName == serviceName
        }
    }

    fun isSelected(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            appContext.inputMethodManager.currentInputMethodInfo?.let {
                it.packageName == BuildConfig.APPLICATION_ID && it.serviceName == serviceName
            } ?: false
        } else {
            getSecureSettings(Settings.Secure.DEFAULT_INPUT_METHOD) == componentName
        }
    }
}