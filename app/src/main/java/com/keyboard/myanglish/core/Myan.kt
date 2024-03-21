package com.keyboard.myanglish.core

import android.content.Context
import androidx.annotation.Keep
import com.keyboard.myanglish.data.clipboard.ClipboardManager
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class Myan(private val context: Context) : MyanAPI, MyanLifecycleOwner {
    private val lifecycleRegistry = MyanLifecycleRegistry()

    override val isReady: Boolean
        get() = lifecycle.currentState == MyanLifecycle.State.READY

    override suspend fun save() {
        TODO("Not yet implemented")
    }

    override suspend fun availableIme(): Array<InputMethodEntry> {
        TODO("Not yet implemented")
    }

    override suspend fun enabledIme(): Array<InputMethodEntry> {
        TODO("Not yet implemented")
    }

    override suspend fun addons(): Array<AddonInfo> {
        TODO("Not yet implemented")
    }

    override val lifecycle: MyanLifecycle
        get() = lifecycleRegistry

    private suspend fun setClipboard(string: String) = withMyanContext { setMyanClipboard(string) }

    private companion object JNI {
        @JvmStatic
        external fun setMyanClipboard(string: String)
    }

    private val dispatcher = MyanDispatcher(object : MyanDispatcher.MyanController {
        override fun nativeStartup() {
            TODO("Not yet implemented")
        }

        override fun nativeLoopOnce() {
            TODO("Not yet implemented")
        }

        override fun nativeScheduleEmpty() {
            TODO("Not yet implemented")
        }

        override fun nativeExit() {
            TODO("Not yet implemented")
        }

    })

    private suspend inline fun <T> withMyanContext(crossinline block: suspend () -> T): T =
        withContext(dispatcher) {
            block()
        }

    @Keep
    private val onClipboardUpdate = ClipboardManager.OnClipboardUpdateListener {
        lifecycle.lifecycleScope.launch { setClipboard(it.text) }
    }

    fun start() {

    }

    fun stop() {
    }
}