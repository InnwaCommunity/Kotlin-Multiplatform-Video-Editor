package com.manage.videoeditor.android

import android.app.Application
import androidx.multidex.MultiDex

class VideoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
    }
}