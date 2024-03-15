package com.keyboard.myanglish.input

import android.inputmethodservice.InputMethodService
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry

open class LifecycleInputMethodService : InputMethodService(), LifecycleOwner {

    private val lifecycleRegistry by lazy { LifecycleRegistry(this) }

    override val lifecycle = lifecycleRegistry
}