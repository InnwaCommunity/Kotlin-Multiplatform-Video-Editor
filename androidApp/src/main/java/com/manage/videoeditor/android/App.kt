package com.manage.videoeditor.android

import android.app.Application
import com.manage.videoeditor.app.FeedStore
import com.manage.videoeditor.core.RssReader
import com.manage.videoeditor.create
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private val appModule = module {
        single { RssReader.create(get(), BuildConfig.DEBUG) }
        single { FeedStore(get()) }
    }

    private fun initKoin() {
        startKoin {
            if(BuildConfig.DEBUG) androidLogger(Level.ERROR)

            androidContext(this@App)
            modules(appModule)
        }
    }
}