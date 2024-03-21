package com.keyboard.myanglish.ui.main.settings.addon

import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.keyboard.myanglish.R
import com.keyboard.myanglish.core.AddonInfo
import com.keyboard.myanglish.ui.common.BaseDynamicListUi
import com.keyboard.myanglish.ui.common.CheckBoxListUi
import com.keyboard.myanglish.ui.common.OnItemChangedListener
import com.keyboard.myanglish.ui.main.settings.ProgressFragment

class AddonListFragment : ProgressFragment(), OnItemChangedListener<AddonInfo> {

    private lateinit var ui : BaseDynamicListUi<AddonInfo>

    private val addonDisplayNames = mutableMapOf<String, String>()

    private fun disableAddon(entry: AddonInfo, reset: () -> Unit) {

    }


    override suspend fun initialize(): View {
        ui = requireContext().CheckBoxListUi(
            initialEntries = myan.runOnReady {
                addons()
                    .sortedBy { it.uniqueName }
                    .onEach { addonDisplayNames[it.uniqueName] = it.displayName }
            },
            initCheckBox = { entry ->
                // remove old listener before change checked state
                setOnCheckedChangeListener(null)
                // our addon shouldn't be disabled
                isEnabled = entry.uniqueName != "androidfrontend"
                isChecked = entry.enabled
                setOnCheckedChangeListener { _, isChecked ->
                    if (!isChecked)
                        disableAddon(entry) { this.isChecked = true }
                    else
                        ui.updateItem(ui.indexItem(entry), entry.copy(enabled = true))
                }
            },
            initSettingsButton = { entry ->
                visibility =
                    if (entry.isConfigurable &&
                        entry.enabled &&
                        // we disable clipboard addon config since we take over the control
                        entry.uniqueName != "clipboard"
                    ) View.VISIBLE else View.INVISIBLE
                setOnClickListener {
//                    it.findNavController().navigate(
//                        R.id.action_addonListFragment_to_addonConfigFragment,
//                        bundleOf(
//                            AddonConfigFragment.ARG_UNIQUE_NAME to entry.uniqueName,
//                            AddonConfigFragment.ARG_NAME to entry.displayName
//                        )
//                    )
                }
            },
            show = { it.displayName }
        )
        ui.addOnItemChangedListener(this)
        return ui.root
    }

}