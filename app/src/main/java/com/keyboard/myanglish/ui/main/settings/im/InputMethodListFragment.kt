package com.keyboard.myanglish.ui.main.settings.im

import android.view.View
import android.widget.AdapterView.OnItemClickListener
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.keyboard.myanglish.R
import com.keyboard.myanglish.core.InputMethodEntry
import com.keyboard.myanglish.ui.common.BaseDynamicListUi
import com.keyboard.myanglish.ui.common.DynamicListUi
import com.keyboard.myanglish.ui.common.OnItemChangedListener
import com.keyboard.myanglish.ui.main.settings.ProgressFragment

class InputMethodListFragment : ProgressFragment(), OnItemChangedListener<InputMethodEntry> {

    private lateinit var ui : BaseDynamicListUi<InputMethodEntry>
    override suspend fun initialize(): View {
        val available = myan.runOnReady { availableIme().toSet() }
        val initialEnabled = myan.runOnReady { enabledIme().toList() }
        ui = requireContext().DynamicListUi(
            mode = BaseDynamicListUi.Mode.ChooseOne {
                (available - entries.toSet()).toTypedArray()
            },
            initialEntries = initialEnabled,
            enableOrder = true,
            initSettingsButton = { entry ->
                setOnClickListener {
                    it.findNavController().navigate(
                        R.id.action_imListFragment_to_imConfigFragment,
                        bundleOf(
                            InputMethodConfigFragment.ARG_UNIQUE_NAME to entry.uniqueName,
                            InputMethodConfigFragment.ARG_NAME to entry.displayName
                        )
                    )
                }
            },
            show = { it.displayName }
        )
        ui.addOnItemChangedListener(this@InputMethodListFragment)
        ui.setViewModel(viewModel)
        viewModel.enableToolbarEditButton(initialEnabled.isNotEmpty()) {
            ui.enterMultiSelect(requireActivity().onBackPressedDispatcher)
        }
        return ui.root
    }

}