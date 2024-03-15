package com.keyboard.myanglish.data.prefs

abstract class ManagedPreferenceProvider {
    private val _managedPreferences: MutableMap<String, ManagedPreference<*>> = mutableMapOf()

    private val _managedPreferenceUi: MutableList<ManagedPreferenceUi<*>> = mutableListOf()

    val managedPreferences: Map<String, ManagedPreference<*>>
        get() = _managedPreferences

    fun ManagedPreferenceUi<*>.registerUi() {
        _managedPreferenceUi.add(this)
    }

    fun ManagedPreference<*>.register() {
        _managedPreferences[key] = this
    }
}