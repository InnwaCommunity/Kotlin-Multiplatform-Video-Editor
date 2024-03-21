package com.keyboard.myanglish.core

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import java.lang.IllegalStateException
import java.util.concurrent.ConcurrentLinkedDeque
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MyanLifecycleRegistry : MyanLifecycle {
    private val observers = ConcurrentLinkedDeque<MyanLifecycleObserver>()

    override fun addObserver(observer: MyanLifecycleObserver) {
        observers.add(observer)
    }

    override fun removeObserver(observer: MyanLifecycleObserver) {
        observers.remove(observer)
    }

    override val currentState: MyanLifecycle.State
        get() = internalState

    private var internalState = MyanLifecycle.State.STOPPED

    override val lifecycleScope: CoroutineScope =
        MyanLifecycleCoroutineScope(this).also { addObserver(it) }

    fun postEvent(event: MyanLifecycle.Event) = synchronized(internalState) {
        when (event) {
            MyanLifecycle.Event.ON_START -> {
                ensureAt(MyanLifecycle.State.STOPPED)
                internalState = MyanLifecycle.State.STARTING
            }

            MyanLifecycle.Event.ON_READY -> {
                ensureAt(MyanLifecycle.State.STARTING)
                internalState = MyanLifecycle.State.READY
            }

            MyanLifecycle.Event.ON_STOP -> {
                ensureAt(MyanLifecycle.State.READY)
                internalState = MyanLifecycle.State.STOPPED
            }

            MyanLifecycle.Event.ON_STOPPED -> {
                ensureAt(MyanLifecycle.State.STOPPING)
                internalState = MyanLifecycle.State.STOPPED
            }
        }
        observers.forEach { it.onStateChanged(event) }
    }

    private fun ensureAt(state: MyanLifecycle.State) = takeIf { (currentState == state) }
        ?: throw IllegalStateException("Currently not at $state!")

}


interface MyanLifecycle {
    val currentState: State
    val lifecycleScope: CoroutineScope

    fun addObserver(observer: MyanLifecycleObserver)
    fun removeObserver(observer: MyanLifecycleObserver)

    enum class State {
        STARTING,
        READY,
        STOPPING,
        STOPPED
    }

    enum class Event {
        ON_START,
        ON_READY,
        ON_STOP,
        ON_STOPPED
    }
}

interface MyanLifecycleOwner {
    val lifecycle: MyanLifecycle
}

val MyanLifecycleOwner.lifeCycleScope
    get() = lifecycle.lifecycleScope

fun interface MyanLifecycleObserver {
    fun onStateChanged(event: MyanLifecycle.Event)
}

class MyanLifecycleCoroutineScope(
    val lifcycle: MyanLifecycle,
    override val coroutineContext: CoroutineContext = SupervisorJob()
) : CoroutineScope, MyanLifecycleObserver {
    override fun onStateChanged(event: MyanLifecycle.Event) {
        if (lifcycle.currentState >= MyanLifecycle.State.STOPPING) {
            coroutineContext.cancelChildren()
        }
    }

}

suspend fun <T> MyanLifecycle.whenAtState(
    state: MyanLifecycle.State,
    block: suspend CoroutineScope.() -> T
): T =
    if (state == currentState) block(lifecycleScope)
    else AtStateHelper(this, state).run(block)

suspend inline fun <T> MyanLifecycle.whenReady(noinline block: suspend CoroutineScope.() -> T) =
    whenAtState(MyanLifecycle.State.READY, block)


private class AtStateHelper(val lifecycle: MyanLifecycle, val state: MyanLifecycle.State) {
    private val observer = MyanLifecycleObserver {
        if (lifecycle.currentState == state)
            continuation?.resume(Unit)
    }

    init {
        lifecycle.addObserver(observer)
    }

    private var continuation: Continuation<Unit>? = null

    suspend fun <T> run(block: suspend CoroutineScope.() -> T): T {
        suspendCoroutine { continuation = it }
        lifecycle.removeObserver(observer)
        return block(lifecycle.lifecycleScope)
    }
}