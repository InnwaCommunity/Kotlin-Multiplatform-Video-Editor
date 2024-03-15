package com.keyboard.myanglish.ui.main.modified

import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.preference.PreferenceViewHolder
import androidx.preference.SwitchPreference
import com.keyboard.myanglish.R

class MySwitchPreference(context: Context) : SwitchPreference(context) {
    override fun onBindViewHolder(holder: PreferenceViewHolder) {
        super.onBindViewHolder(holder)
        holder.itemView.setOnLongClickListener {
            AlertDialog.Builder(context)
                .setTitle(title ?: "Preference")
                .setMessage(R.string.whether_reset_switch_preference)
                .setNegativeButton(android.R.string.cancel) { _, _ -> }
                .setPositiveButton(R.string.reset) { _, _ -> restore() }
                .show()
            true
        }
    }
}