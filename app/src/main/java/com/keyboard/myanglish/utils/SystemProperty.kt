package com.keyboard.myanglish.utils

import android.annotation.SuppressLint

@SuppressLint("PrivateApi")
fun getSystemProperty(key: String): String {
    return try {
        Class.forName("android.os.SystemProperties")
            .getMethod("get", String::class.java)
            .invoke(null, key) as String
    } catch (e: Exception) {
        ""
    }
}