package com.keyboard.myanglish.ui.main

import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.keyboard.myanglish.R
import com.keyboard.myanglish.core.data.DataManager
import com.keyboard.myanglish.data.prefs.AppPrefs
import com.keyboard.myanglish.ui.common.PaddingPreferenceFragment
import com.keyboard.myanglish.ui.main.modified.MySwitchPreference
import com.keyboard.myanglish.utils.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.launch
import java.io.File
import com.keyboard.myanglish.utils.addPreference
import com.keyboard.myanglish.utils.startActivity
import kotlinx.coroutines.withContext

class DeveloperFragment : PaddingPreferenceFragment() {

    private lateinit var hprofFile : File
    private lateinit var launcher : ActivityResultLauncher<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launcher = registerForActivityResult(ActivityResultContracts.CreateDocument("application/octet-stream")) { uri ->
            if(uri == null) return@registerForActivityResult
            val ctx = requireContext()
            lifecycleScope.launch(NonCancellable + Dispatchers.IO) {
                runCatching {
                    ctx.contentResolver.openOutputStream(uri)!!.use { o ->
                        hprofFile.inputStream().use { i -> i.copyTo(o) }
                    }
                }.let { ctx.toast(it) }
            }
            hprofFile.delete()
        }
    }


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        preferenceScreen = preferenceManager.createPreferenceScreen(requireContext()).apply {
            addPreference(R.string.real_time_logs) {
                startActivity<LogActivity>()
            }
            addPreference(MySwitchPreference(context).apply {
                key = AppPrefs.getInstance().internal.verboseLog.key
                setTitle(R.string.verbose_log)
                setSummary(R.string.verbose_log_summary)
                setDefaultValue(false)
                isIconSpaceReserved = false
                isSingleLineTitle = false
            })
            addPreference(MySwitchPreference(context).apply {
                key = AppPrefs.getInstance().internal.editorInfoInspector.key
                setTitle(R.string.editor_info_inspector)
                setDefaultValue(false)
                isIconSpaceReserved = false
                isSingleLineTitle = false
            })
            addPreference(R.string.delete_and_sync_data){
                AlertDialog.Builder(context)
                    .setTitle(R.string.delete_and_sync_data)
                    .setMessage(R.string.delete_and_sync_data_message)
                    .setPositiveButton(android.R.string.ok) {_, _ ->
                        lifecycleScope.launch(Dispatchers.IO) {
                            DataManager.deleteAndSync()
                            withContext(Dispatchers.Main) {
                                context.toast(R.string.synced)
                            }
                        }
                    }
                    .setNegativeButton(android.R.string.cancel, null)
                    .show()
            }
        }
    }
}