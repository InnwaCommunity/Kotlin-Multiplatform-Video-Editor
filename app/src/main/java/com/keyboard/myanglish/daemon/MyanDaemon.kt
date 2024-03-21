package com.keyboard.myanglish.daemon

import com.keyboard.myanglish.core.Myan
import com.keyboard.myanglish.core.MyanAPI
import com.keyboard.myanglish.core.MyanLifecycle
import com.keyboard.myanglish.core.lifeCycleScope
import com.keyboard.myanglish.core.whenReady
import com.keyboard.myanglish.utils.appContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

/**
 * Manage the singleton instance of [Fcitx]
 *
 * To use fcitx, client should call [connect] to obtain a [FcitxConnection],
 * and call [disconnect] on client destroyed. Client should not leak the instance of [FcitxAPI],
 * and must use [FcitxConnection] to access fcitx functionalities.
 *
 * The instance of [Fcitx] always exists,but whether the dispatcher runs and callback works depend on clients, i.e.
 * if no clients are connected, [Fcitx.stop] will be called.
 *
 * Functions are thread-safe in this class.
 */
object MyanDaemon {

    private val realMyan by lazy { Myan(appContext) }

    // don't leak fcitx instance
    private val myanImpl by lazy { object : MyanAPI by realMyan {} }

    private fun mkConnection(name: String) = object : MyanConnection {
        private inline fun <T> ensureConnected(block: () -> T) =
            if (name in clients)
                block()
            else throw IllegalStateException("$name is disconnected")
        override fun <T> runImmediately(block: suspend MyanAPI.() -> T): T = ensureConnected {
            runBlocking(realMyan.lifeCycleScope.coroutineContext) {
                block(myanImpl)
            }
        }

        override suspend fun <T> runOnReady(block: suspend MyanAPI.() -> T): T = ensureConnected {
            realMyan.lifecycle.whenReady { block(myanImpl) }
        }

        override fun runIfReady(block: suspend MyanAPI.() -> Unit) {
            ensureConnected {
                if (realMyan.isReady)
                    realMyan.lifeCycleScope.launch {
                        block(myanImpl)
                    }
            }
        }

        override val lifecycleScope: CoroutineScope
            get() = TODO("Not yet implemented")


    }

    private val lock = ReentrantLock()

    private val clients = mutableMapOf<String, MyanConnection>()

    /**
     * Create a connection
     */
    fun connect(name: String): MyanConnection = lock.withLock {
        if (name in clients)
            return@withLock clients.getValue(name)
        if (realMyan.lifecycle.currentState == MyanLifecycle.State.STOPPED) {
            Timber.d("FcitxDaemon start fcitx")
            realMyan.start()
        }
        val new = mkConnection(name)
        clients[name] = new
        return@withLock new
    }

    fun getFirstConnectionOrNull() = clients.firstNotNullOfOrNull { it.value }

    fun stopMyan() {
        realMyan.stop()
    }
}