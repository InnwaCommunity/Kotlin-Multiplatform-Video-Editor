package com.manage.videoeditor

actual fun timestampMs(): Long {
    return  System.currentTimeMillis()
}