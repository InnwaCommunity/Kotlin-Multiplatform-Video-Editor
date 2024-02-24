package com.manage.videoeditor

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform