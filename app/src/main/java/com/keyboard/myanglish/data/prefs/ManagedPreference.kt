package com.keyboard.myanglish.data.prefs

import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import androidx.core.content.edit
import com.google.android.material.slider.Slider.OnChangeListener
import com.keyboard.myanglish.utils.WeakHashSet
import java.lang.Exception
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

abstract class ManagedPreference<T : Any>(
    val sharedPreferences: SharedPreferences,
    val key: String,
    val defaultValue: T,
) : ReadWriteProperty<Any?, T> {

    fun interface OnChangeListener<in T : Any> {
        fun onChange(key: String, value: T)
    }

    abstract fun setValue(value: T)

    abstract fun getValue(): T

    abstract fun putValueTo(editor: SharedPreferences.Editor)

    override fun getValue(thisRef: Any?, property: KProperty<*>): T = getValue()

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) = setValue(value)

    private val listeners by lazy {
        WeakHashSet<OnChangeListener<T>>()
    }

    fun registerOnChangeListener(listener: OnChangeListener<T>) {
        listeners.add(listener)
    }

    fun unregisterOnChangeListener(listener: OnChangeListener<T>) {
        listeners.remove(listener)
    }

    fun fireChange() {
        if (listeners.isEmpty()) return
        val newValue = getValue()
        listeners.forEach { it.onChange(key, newValue) }
    }

    class PBool(sharedPreferences: SharedPreferences, key: String, defaultValue: Boolean) :
        ManagedPreference<Boolean>(sharedPreferences, key, defaultValue) {

        override fun setValue(value: Boolean) {
            sharedPreferences.edit { putBoolean(key, value) }
        }

        override fun getValue(): Boolean {
            return try {
                sharedPreferences.getBoolean(key, defaultValue)
            } catch (e: Exception) {
                setValue(defaultValue)
                defaultValue
            }
        }

        override fun putValueTo(editor: SharedPreferences.Editor) {
            editor.putBoolean(key, getValue())
        }
    }

    class PString(sharedPreferences: SharedPreferences, key: String, defaultValue: String) :
        ManagedPreference<String>(sharedPreferences, key, defaultValue) {
        override fun setValue(value: String) {
            sharedPreferences.edit { putString(key, value) }
        }

        override fun getValue(): String {
            return try {
                sharedPreferences.getString(key, defaultValue)!!
            } catch (e: Exception) {
                setValue(defaultValue)
                defaultValue
            }
        }

        override fun putValueTo(editor: Editor) {
            editor.putString(key, getValue())
        }

    }

    class PInt(sharedPreferences: SharedPreferences, key: String, defaultValue: Int) :
        ManagedPreference<Int>(sharedPreferences, key, defaultValue) {
        override fun setValue(value: Int) {
            sharedPreferences.edit { putInt(key, value) }
        }

        override fun getValue(): Int {
            return try {
                sharedPreferences.getInt(key, defaultValue)
            } catch (e: Exception) {
                setValue(defaultValue)
                defaultValue
            }
        }

        override fun putValueTo(editor: Editor) {
            editor.putInt(key, getValue())
        }

    }
}