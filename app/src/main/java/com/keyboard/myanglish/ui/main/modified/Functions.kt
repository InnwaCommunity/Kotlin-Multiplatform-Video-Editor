package com.keyboard.myanglish.ui.main.modified

import androidx.preference.Preference
import androidx.preference.SwitchPreference

private val mDefault by lazy {
    Preference::class.java
        .getDeclaredField("mDefaultValue")
        .apply { isAccessible = true }
}

private fun <T : Preference> T.def() =
    mDefault.get(this)

fun <T : SwitchPreference> T.restore() {
    (def() as? Boolean)?.let {
        if (callChangeListener(it)) {
            isChecked = it
        }
    }
}
