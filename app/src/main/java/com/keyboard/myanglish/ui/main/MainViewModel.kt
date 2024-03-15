package com.keyboard.myanglish.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.keyboard.myanglish.R
import com.keyboard.myanglish.utils.appContext

class MainViewModel : ViewModel() {
    val toolbarTitle = MutableLiveData(appContext.getString(R.string.app_name))

    val toolbarShadow = MutableLiveData(true)

    fun disableToolbarShadow() {
        toolbarShadow.value = false
    }

    fun enableToolbarShadow() {
        toolbarShadow.value = true
    }
}