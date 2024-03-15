package com.keyboard.myanglish.ui.setup

import com.keyboard.myanglish.utils.InputMethodUtil

enum class SetupPage {
    Enable, Select;
    fun isDone() = when (this) {
        Enable -> InputMethodUtil.isEnabled()
        Select -> InputMethodUtil.isSelected()
    }

    companion object {
        fun valueOf(value: Int) = entries[value]
        fun SetupPage.isLastPage() = this == entries.last()
        fun Int.isLastPage() = this == entries.size - 1
        fun hasUndonePage() = entries.any { !it.isDone() }
        fun firstUndonePage() = entries.firstOrNull { !it.isDone() }
    }
}