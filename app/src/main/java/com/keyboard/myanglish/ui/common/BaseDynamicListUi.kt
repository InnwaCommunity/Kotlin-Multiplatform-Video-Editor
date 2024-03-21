package com.keyboard.myanglish.ui.common

import android.content.Context
import android.view.Display.Mode
import android.view.View
import android.widget.CheckBox
import android.widget.ImageButton
import splitties.views.dsl.coordinatorlayout.coordinatorLayout
import splitties.views.dsl.core.Ui

abstract class BaseDynamicListUi<T>(
    override val ctx: Context,
    private val mode: Mode<T>,
    initialEntries: List<T>,
    enableOrder: Boolean = false,
    initCheckBox: (CheckBox.(T) -> Unit) = { visibility = View.GONE },
    initSettingsButton: (ImageButton.(T) -> Unit) = { visibility = View.GONE },
) : Ui,
    DynamicListAdapter<T>(
        initialEntries,
        enableAddAndDelete = mode !is Mode.Immutable,
        enableOrder,
        initCheckBox,
        initEditButton = {},
        initSettingsButton
    ) {
    sealed class Mode<T> {
        /**
         * Pick one from a list of [T]
         */
        data class ChooseOne<T>(val candidatesSource: BaseDynamicListUi<T>.() -> Array<T>) :
            Mode<T>()

        class Immutable<T> : Mode<T>()
    }



    override val root = coordinatorLayout {  }


}