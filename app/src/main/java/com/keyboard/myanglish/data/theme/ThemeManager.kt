package com.keyboard.myanglish.data.theme

import android.content.res.Configuration
import android.os.Build
import androidx.annotation.Keep
import androidx.annotation.RequiresApi
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.keyboard.myanglish.data.prefs.AppPrefs
import com.keyboard.myanglish.data.prefs.ManagedPreference
import com.keyboard.myanglish.utils.WeakHashSet
import com.keyboard.myanglish.utils.appContext
import com.keyboard.myanglish.utils.isDarkMode


object ThemeManager {

    fun interface OnThemeChangeListener {
        fun onThemeChange(theme: Theme)
    }

    val BuiltinThemes = listOf(
        ThemePreset.MaterialLight,
        ThemePreset.MaterialDark,
        ThemePreset.PixelLight,
        ThemePreset.PixelDark,
        ThemePreset.NordLight,
        ThemePreset.NordDark,
        ThemePreset.DeepBlue,
        ThemePreset.Monokai,
        ThemePreset.AMOLEDBlack,
    )

    val DefaultTheme = ThemePreset.PixelDark

    private val customThemes: MutableList<Theme.Custom> = ThemeFilesManager.listThemes()

    fun getTheme(name: String) =
        customThemes.find { it.name == name } ?: BuiltinThemes.find { it.name == name }

    fun getAllThemes() = customThemes + BuiltinThemes


    private lateinit var _activeTheme: Theme

    val prefs = AppPrefs.getInstance().registerProvider(::ThemePrefs)

    var activeTheme: Theme
        get() = _activeTheme
        private set(value) {
            if (_activeTheme == value) return
            _activeTheme = value
            fireChange()
        }

    private var isDarkMode = false

    private val onChangeListeners = WeakHashSet<OnThemeChangeListener>()

    private fun fireChange() {
        onChangeListeners.forEach { it.onThemeChange(_activeTheme) }
    }

    private fun evaluateActiveTheme(): Theme {
        return if (prefs.followSystemDayNightTheme.getValue()) {
            if (isDarkMode) prefs.darkModeTheme else prefs.lightModeTheme
        } else {
            prefs.normalModeTheme
        }.getValue()
    }

    @Keep
    private val onThemePrefsChange = ManagedPreference.OnChangeListener<Any> { key, _ ->
        if (prefs.dayNightModePrefNames.contains(key)) {
            activeTheme = evaluateActiveTheme()
        } else {
            fireChange()
        }
    }

    fun init(configuration: Configuration) {
        isDarkMode = configuration.isDarkMode()
        prefs.managedPreferences.values.forEach {
            it.registerOnChangeListener(onThemePrefsChange)
        }
        _activeTheme = evaluateActiveTheme()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun syncToDeviceEncryptedStorage() {
        val ctx = appContext.createDeviceProtectedStorageContext()
        val sp = PreferenceManager.getDefaultSharedPreferences(ctx)
        sp.edit {
            prefs.managedPreferences.forEach {
                it.value.putValueTo(this@edit)
            }
        }
    }
}