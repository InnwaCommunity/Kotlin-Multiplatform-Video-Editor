package com.keyboard.myanglish.utils

import android.content.Context
import android.os.UserManager
import android.view.inputmethod.InputMethodManager
import androidx.core.content.getSystemService

val Context.inputMethodManager
    get() = getSystemService<InputMethodManager>()!!

val Context.userManager
    get() = getSystemService<UserManager>()!!