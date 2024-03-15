package com.keyboard.myanglish.input.picker

import android.content.Context
import android.view.View
import androidx.compose.ui.unit.Density
import com.keyboard.myanglish.R
import com.keyboard.myanglish.core.KeySym
import com.keyboard.myanglish.data.prefs.AppPrefs
import com.keyboard.myanglish.data.theme.Theme
import com.keyboard.myanglish.input.keyboard.KeyAction
import com.keyboard.myanglish.input.keyboard.KeyDef
import splitties.views.dsl.core.Ui

class PickerPageUi(override val ctx: Context, val theme: Theme, private val density: Density) : Ui {

    enum class Density(
        val pageSize: Int,
        val columnCount: Int,
        val rowCount: Int,
        val textSize: Float,
        val showBackspace: Boolean
    ) {
        // symbol: 10/10/8, backspace on bottom right
        High(28, 10, 3, 19f, true),

        // emoji: 7/7/6, backspace on bottom right
        Medium(20, 7, 3, 23.7f, true),

        // emoticon: 4/4/4, no backspace
        Low(12, 4, 3, 19f, false)
    }

    companion object {
        val BackspaceAppearance = KeyDef.Appearance.Image(
            src = R.drawable.ic_baseline_backspace_24,
            variant = KeyDef.Appearance.Variant.Alternative,
            border = KeyDef.Appearance.Border.Off,
            viewId = R.id.button_backspace
        )

//        val BackspaceAction = KeyAction.SymAction(KeySym(FcitxKeyMapping.FcitxKey_BackSpace))

//        private var popupOnKeyPress by AppPrefs.getInstance().keyboard.popupOnKeyPress
    }

    override val root: View
        get() = TODO("Not yet implemented")
}