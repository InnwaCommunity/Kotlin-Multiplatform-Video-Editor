package com.keyboard.myanglish.ui.common

import android.content.Context
import androidx.preference.Preference
import com.keyboard.myanglish.data.theme.Theme

class ThemeSelectPreference(context: Context, private val defaultTheme: Theme) :
    Preference(context) {
    init {
        setDefaultValue(defaultTheme.name)
    }
}