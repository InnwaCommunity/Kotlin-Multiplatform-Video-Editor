package com.keyboard.myanglish.ui.main.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.keyboard.myanglish.ui.common.withLoadingDialog
import com.keyboard.myanglish.ui.main.MainViewModel
import splitties.views.dsl.core.frameLayout

abstract class ProgressFragment : Fragment() {

    private lateinit var root: FrameLayout

    @Volatile
    protected var isInitialized = false
        private set

    abstract suspend fun initialize(): View

    protected val viewModel: MainViewModel by activityViewModels()

    protected val myan
        get() = viewModel.myan

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return requireContext().frameLayout().also { root = it }
    }

    final override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lifecycleScope.withLoadingDialog(requireContext()) {
            val newView = initialize().apply { alpha = 0f }
            isInitialized = true
            root.removeAllViews()
            root.addView(newView)
            newView.animate().setDuration(150L).alphaBy(1f)
        }
    }
}