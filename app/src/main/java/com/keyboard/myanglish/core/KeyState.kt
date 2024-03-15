package com.keyboard.myanglish.core

@Suppress("Unused", "EnumEntryName")
enum class KeyState(val state: UInt) {
    NoState(0u),
    Virtual(1u shl 29)
}

@JvmInline
value class KeyStates(val states: UInt) {

    constructor(vararg states: KeyState) : this(mergeStates(states))

    companion object {
        fun mergeStates(arr: Array<out KeyState>): UInt =
            arr.fold(KeyState.NoState.state) { acc, it -> acc or it.state }
    }

}