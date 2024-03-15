package com.keyboard.myanglish.input.picker

import androidx.compose.ui.unit.Density
import com.keyboard.myanglish.input.keyboard.KeyDef
import com.keyboard.myanglish.input.wm.EssentialWindow
import com.keyboard.myanglish.input.wm.InputWindow

class PickerWindow(
    override val key: Key,
    val data: List<Pair<PickerData.Category, Array<String>>>,
    val density: PickerPageUi.Density,
    private val switchKey: KeyDef,
    val popupPreview: Boolean = true
) : InputWindow.ExtendedInputWindow<PickerWindow>(), EssentialWindow {

    enum class Key : EssentialWindow.Key {
        Symbol,
        Emoji,
        Emoticon
    }

}