package com.keyboard.myanglish.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import timber.log.Timber
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.coroutines.CoroutineContext

class MyanDispatcher(private val controller: MyanController) : CoroutineDispatcher() {

    class WrappedRunnable(private val runnable: Runnable, private val name: String? = null) :
        Runnable by runnable {

    }

    interface MyanController {
        fun nativeStartup()
        fun nativeLoopOnce()
        fun nativeScheduleEmpty()
        fun nativeExit()
    }

    private val runningLock = Mutex()

    private val queue = ConcurrentLinkedQueue<WrappedRunnable>()

    private val isRunning = AtomicBoolean(false)

    fun stop(): List<Runnable> {
        Timber.i("FcitxDispatcher stop()")
        return if (isRunning.compareAndSet(true, false)) {
            runBlocking {
                controller.nativeScheduleEmpty()
                runningLock.withLock {
                    val rest = queue.toList()
                    queue.clear()
                    rest
                }
            }
        } else emptyList()
    }

    override fun dispatch(context: CoroutineContext, block: Runnable) {
        TODO("Not yet implemented")
    }
}