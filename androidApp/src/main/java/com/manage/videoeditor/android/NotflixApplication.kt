package com.manage.videoeditor.android

import android.app.Application
import com.manage.videoeditor.utils.ContextUtils

class NotflixApplication : Application {

    override fun onCreate() {
        super.onCreate()

        ContextUtils.setContext(context = this)

        initKo
    }
}