package com.keyboard.myanglish.ui.main

import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceCategory
import com.keyboard.myanglish.R
import com.keyboard.myanglish.ui.common.PaddingPreferenceFragment
import com.keyboard.myanglish.utils.addCategory
import com.keyboard.myanglish.utils.addPreference

class MainFragment : PaddingPreferenceFragment() {

    private fun PreferenceCategory.addDestinationPreference(
        @StringRes title: Int,
        @DrawableRes icon: Int,
        @IdRes destination: Int
    ) {
        addPreference(title, icon = icon) {
            findNavController().navigate(destination)
        }
    }
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        preferenceScreen = preferenceManager.createPreferenceScreen(requireContext()).apply {
            addCategory("Myan") {
                addDestinationPreference(
                    R.string.global_options,
                    R.drawable.ic_baseline_tune_24,
                    R.id.action_mainFragment_to_globalConfigFragment
                )
            }
        }
    }
}