package com.keyboard.myanglish.input.keyboard

import com.keyboard.myanglish.core.KeyState
import com.keyboard.myanglish.core.KeyStates
import com.keyboard.myanglish.core.KeySym

sealed class KeyAction {

    data class MyanKeyAction(var act: String) : KeyAction()

    data class SymAction(val sym: KeySym, val states: KeyStates = VirtualState) : KeyAction() {
        companion object {
            val VirtualState = KeyStates(KeyState.Virtual)
        }
    }
}