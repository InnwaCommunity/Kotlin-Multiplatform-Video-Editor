package com.keyboard.myanglish.core

interface MyanAPI {

    val isReady: Boolean
    suspend fun save()

    suspend fun availableIme(): Array<InputMethodEntry>

    suspend fun enabledIme() : Array<InputMethodEntry>

    suspend fun addons(): Array<AddonInfo>
}