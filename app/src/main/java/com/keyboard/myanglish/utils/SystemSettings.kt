package com.keyboard.myanglish.utils

import android.provider.Settings

fun getSecureSettings(name: String): String? {
    return Settings.Secure.getString(appContext.contentResolver, name)
}