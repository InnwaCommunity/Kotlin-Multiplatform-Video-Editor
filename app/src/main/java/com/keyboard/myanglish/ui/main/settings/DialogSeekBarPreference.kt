package com.keyboard.myanglish.ui.main.settings

import android.content.Context
import android.util.AttributeSet
import androidx.preference.DialogPreference
import com.keyboard.myanglish.R

class DialogSeekBarPreference @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = androidx.preference.R.attr.preferenceStyle
) : DialogPreference(context, attrs, defStyleAttr) {

    private var value = 0
    var min: Int
    var max: Int
    var step: Int
    var unit: String

    var default: Int? = null
    var defaultLabel: String? = null

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.DialogSeekBarPreference, 0, 0).run {
            try {
                min = getInteger(R.styleable.DialogSeekBarPreference_min, 0)
                max = getInteger(R.styleable.DialogSeekBarPreference_max, 100)
                step = getInteger(R.styleable.DialogSeekBarPreference_step, 1)
                unit = getString(R.styleable.DialogSeekBarPreference_unit) ?: ""
                if (getBoolean(R.styleable.DialogSeekBarPreference_useSimpleSummaryProvider, false)) {
                    summaryProvider = SimpleSummaryProvider
                }
            } finally {
                recycle()
            }
        }
    }

    private fun textForValue(value: Int = this@DialogSeekBarPreference.value): String =
        if (value == default && defaultLabel != null) defaultLabel!! else "$value $unit"

    object SimpleSummaryProvider : SummaryProvider<DialogSeekBarPreference> {
        override fun provideSummary(preference: DialogSeekBarPreference): CharSequence {
            return preference.textForValue()
        }
    }
}
