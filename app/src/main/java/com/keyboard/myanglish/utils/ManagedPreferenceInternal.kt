package com.keyboard.myanglish.utils

import android.content.SharedPreferences
import com.keyboard.myanglish.data.prefs.ManagedPreference
import com.keyboard.myanglish.data.prefs.ManagedPreferenceProvider

abstract class ManagedPreferenceInternal(private val sharedPreferences: SharedPreferences) :
    ManagedPreferenceProvider() {

    protected fun int(key: String, defaultValue: Int) =
        ManagedPreference.PInt(sharedPreferences, key, defaultValue).apply { register() }

    protected fun string(key: String, defaultValue: String) =
        ManagedPreference.PString(sharedPreferences, key, defaultValue).apply { register() }

    protected fun bool(key: String, defaultValue: Boolean) =
        ManagedPreference.PBool(sharedPreferences, key, defaultValue).apply { register() }
}