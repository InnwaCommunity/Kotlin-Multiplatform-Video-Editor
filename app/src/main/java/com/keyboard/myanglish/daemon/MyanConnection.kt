package com.keyboard.myanglish.daemon

import com.keyboard.myanglish.core.MyanAPI
import kotlinx.coroutines.CoroutineScope

/**
 * Clients should use [FcitxConnection] to run fcitx operations.
 */
interface MyanConnection {

    /**
     * Run an operation immediately
     * The suspended [block] will be executed in caller's thread.
     * Use this function only for non-blocking operations like
     * accessing [FcitxAPI.eventFlow].
     */
    fun <T> runImmediately(block: suspend MyanAPI.() -> T): T

    /**
     * Run an operation immediately if fcitx is at ready state.
     * Otherwise, caller will be suspended until fcitx is ready and operation is done.
     * The suspended [block] will be executed in caller's thread.
     * Client should use this function in most cases.
     */
    suspend fun <T> runOnReady(block: suspend MyanAPI.() -> T): T

    /**
     * Run an operation if fcitx is at ready state.
     * Otherwise, do nothing.
     * The suspended [block] will be executed in thread pool.
     * This function does not block or suspend the caller.
     */
    fun runIfReady(block: suspend MyanAPI.() -> Unit)

    val lifecycleScope: CoroutineScope
}