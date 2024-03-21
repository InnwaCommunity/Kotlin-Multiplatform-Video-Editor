package com.keyboard.myanglish.ui.common

import android.content.Context
import android.view.View
import com.keyboard.myanglish.R
import splitties.resources.drawable
import splitties.resources.resolveThemeAttribute
import splitties.resources.styledColor
import splitties.resources.styledDrawable
import splitties.views.dsl.constraintlayout.constraintLayout
import splitties.views.dsl.core.Ui
import splitties.views.dsl.core.checkBox
import splitties.views.dsl.core.imageButton
import splitties.views.dsl.core.imageView
import splitties.views.dsl.core.textView
import splitties.views.imageDrawable
import splitties.views.setPaddingDp
import splitties.views.textAppearance

class DynamicListEntryUi(override val ctx: Context) : Ui {

    val handleImage = imageView {
        imageDrawable = drawable(R.drawable.ic_baseline_drag_handle_24)!!.apply {
            setTint(styledColor(android.R.attr.colorAccent))
        }
        setPaddingDp(3, 0, 3, 0)
    }

    val multiselectCheckBox = checkBox()

    val checkBox = checkBox()

    val nameText = textView {
        setPaddingDp(0, 16, 0, 16)
        textAppearance = ctx.resolveThemeAttribute(android.R.attr.textAppearanceListItem)
    }

    val editButton = imageButton {
        background = styledDrawable(android.R.attr.selectableItemBackground)
        imageDrawable = drawable(R.drawable.ic_baseline_edit_24)!!.apply {
            setTint(styledColor(android.R.attr.colorControlNormal))
        }
    }

    val settingsButton = imageButton {
        background = styledDrawable(android.R.attr.selectableItemBackground)
        imageDrawable = drawable(R.drawable.ic_baseline_settings_24)!!.apply {
            setTint(styledColor(android.R.attr.colorControlNormal))
        }
    }

    override val root: View = constraintLayout {

    }
}