package com.keyboard.myanglish.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.CallSuper
import com.keyboard.myanglish.ui.main.modified.MyPreferenceFragment
import com.keyboard.myanglish.utils.applyNavBarInsetsBottomPadding

abstract class PaddingPreferenceFragment : MyPreferenceFragment() {
    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = super.onCreateView(inflater, container, savedInstanceState).apply {
        listView.applyNavBarInsetsBottomPadding()
    }
}