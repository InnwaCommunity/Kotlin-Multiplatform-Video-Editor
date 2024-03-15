package com.keyboard.myanglish.ui.main.settings

import android.content.Context
import androidx.preference.EditTextPreference

class EditTextIntPreference(context: Context) : EditTextPreference(context) {

    private var value = 0
    var min: Int = Int.MIN_VALUE
    var max: Int = Int.MAX_VALUE
    var unit: String = ""

    private val currentValue: Int
        get() = getPersistedInt(value)


    object SimpleSummaryProvider : SummaryProvider<EditTextIntPreference> {
        override fun provideSummary(preference: EditTextIntPreference): CharSequence {
            return preference.run { "$currentValue $unit" }
        }
    }
}