package com.keyboard.myanglish.ui.setup

import androidx.fragment.app.FragmentActivity

class SetupActivity : FragmentActivity() {
    companion object {
        private var shown = false
        private const val CHANNEL_ID = "setup"
        private const val NOTIFY_ID = 233
        fun shouldShowUp() = !shown && SetupPage.hasUndonePage()
    }
}