package com.keyboard.myanglish.utils

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.preference.Preference
import androidx.preference.PreferenceCategory
import androidx.preference.PreferenceGroup
import androidx.preference.PreferenceScreen
import splitties.resources.drawable
import splitties.resources.styledColor

fun PreferenceScreen.addCategory(title: String, block: PreferenceCategory.() -> Unit) {
    val category = PreferenceCategory(context).apply {
        setTitle(title)
    }
    addPreference(category)
    block.invoke(category)
}

fun PreferenceGroup.addPreference(
    title: String,
    summary: String? = null,
    @DrawableRes icon: Int? = null,
    onClick: (() -> Unit)? = null
) {
    addPreference(Preference(context).apply {
        isSingleLineTitle = false
        setTitle(title)
        setSummary(summary)
        if (icon == null) {
            isIconSpaceReserved = false
        } else {
            setIcon(context.drawable(icon)?.apply {
                setTint(context.styledColor(android.R.attr.colorControlNormal))
            })
        }
        onClick?.also {
            setOnPreferenceClickListener { _ ->
                it.invoke()
                true
            }
        }
    })
}

fun PreferenceGroup.addPreference(
    @StringRes title: Int,
    summary: String,
    @DrawableRes icon: Int? = null,
    onClick: (() -> Unit)? = null
) {
    val ctx = context
    addPreference(ctx.getString(title), summary, icon, onClick)
}

fun PreferenceGroup.addPreference(
    @StringRes title: Int,
    @StringRes summary: Int? = null,
    @DrawableRes icon: Int? = null,
    onClick: (() -> Unit)? = null
) {
    val ctx = context
    addPreference(ctx.getString(title), summary?.let(ctx::getString), icon, onClick)
}
