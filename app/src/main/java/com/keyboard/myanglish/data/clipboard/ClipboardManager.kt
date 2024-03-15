package com.keyboard.myanglish.data.clipboard

import android.content.ClipboardManager
import android.content.Context
import androidx.annotation.Keep
import androidx.room.Room
import com.keyboard.myanglish.data.clipboard.db.ClipboardDao
import com.keyboard.myanglish.data.clipboard.db.ClipboardDatabase
import com.keyboard.myanglish.data.clipboard.db.ClipboardEntry
import com.keyboard.myanglish.data.prefs.AppPrefs
import com.keyboard.myanglish.data.prefs.ManagedPreference
import com.keyboard.myanglish.utils.WeakHashSet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import splitties.systemservices.clipboardManager

object ClipboardManager : ClipboardManager.OnPrimaryClipChangedListener,
    CoroutineScope by CoroutineScope(SupervisorJob() + Dispatchers.Default) {
    private lateinit var clbDb: ClipboardDatabase
    private lateinit var clbDao: ClipboardDao

    fun interface OnClipboardUpdateListener {
        fun onUpdate(entry: ClipboardEntry)
    }

    var itemCount: Int = 0
        private set

    private suspend fun updateItemCount() {

    }

    private val onUpdateListeners = WeakHashSet<OnClipboardUpdateListener>()

    fun addOnUpdateListener(listener: OnClipboardUpdateListener) {
        onUpdateListeners.add(listener)
    }

    fun removeOnUpdateListener(listener: OnClipboardUpdateListener) {
        onUpdateListeners.remove(listener)
    }

    private val enablePref = AppPrefs.getInstance().clipboard.clipboardListening

    @Keep
    private val enableListener = ManagedPreference.OnChangeListener<Boolean> { _, value ->
        if (value) {
            clipboardManager.addPrimaryClipChangedListener(this)
        } else {
            clipboardManager.removePrimaryClipChangedListener(this)
        }
    }

    private val limitPref = AppPrefs.getInstance().clipboard.clipboardHistoryLimit

    @Keep
    private val limitListener = ManagedPreference.OnChangeListener<Int> { _, _ ->
        launch { removeOutdated() }
    }

    fun init(context: Context) {
        clbDb = Room
            .databaseBuilder(context, ClipboardDatabase::class.java, "clbdb")
            .build()
        clbDao = clbDb.clipboardDao()
        enableListener.onChange(enablePref.key, enablePref.getValue())
        enablePref.registerOnChangeListener(enableListener)
        limitListener.onChange(limitPref.key, limitPref.getValue())
        limitPref.registerOnChangeListener(limitListener)
        launch { upda }
    }

    override fun onPrimaryClipChanged() {
        TODO("Not yet implemented")
    }

    private suspend fun removeOutdated() {
        val limit = limitPref.getValue()
        val unpinned = clbDao.getAllUnpinned()
        if (unpinned.size > limit) {
            val last = unpinned
                .sortedBy { it.id }
                .getOrNull(unpinned.size - limit)
            clbDao.markUnpinnedAsDeletedEarlierThan(last?.timestamp ?: System.currentTimeMillis())
        }
    }
}