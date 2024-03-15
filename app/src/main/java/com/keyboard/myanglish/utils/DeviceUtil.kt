package com.keyboard.myanglish.utils

object DeviceUtil {
    val isVivoOriginOS: Boolean by lazy {
        getSystemProperty("ro.vivo.os.version").isNotEmpty()
    }
}