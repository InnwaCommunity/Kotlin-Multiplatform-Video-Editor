package com.keyboard.myanglish.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment

inline fun <reified T : Activity> Context.startActivity(setupIntent: Intent.() -> Unit = {}) {
    startActivity(Intent(this, T::class.java).apply(setupIntent))
}

inline fun <reified T : Activity> Fragment.startActivity(setupIntent: Intent.() -> Unit = {}) {
    requireContext().startActivity<T>(setupIntent)
}
