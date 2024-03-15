package com.keyboard.myanglish.input.wm

interface EssentialWindow {
    interface Key

    /**
     * Since the window is saved in the window manager,
     * we need a key to discriminate between other essential windows
     */
    val key: Key

    /**
     * Before the window was added to window manager's layout
     */
    fun beforeAttached() {}
}