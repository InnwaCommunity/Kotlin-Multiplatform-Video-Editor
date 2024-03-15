package com.keyboard.myanglish.core

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import com.keyboard.myanglish.utils.appContext
import timber.log.Timber

object MyanPluginServices {

    class PluginServiceConnection(
        private val pluginId: String,
        private val onDied: () -> Unit
    ) : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            TODO("Not yet implemented")
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            TODO("Not yet implemented")
        }

    }

    private val connections = mutableMapOf<String, PluginServiceConnection>()

    fun disconnectAll() {
        connections.forEach { (name, connection) ->
            appContext.unbindService(connection)
            Timber.d("Unbound plugin: $name")
        }
        connections.clear()
    }
}