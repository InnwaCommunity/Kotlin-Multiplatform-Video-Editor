package com.keyboard.myanglish.ui.common

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.RecyclerView
import com.keyboard.myanglish.ui.main.MainViewModel

abstract class DynamicListAdapter<T>(
    initialEntries: List<T>,
    val enableAddAndDelete: Boolean = true,
    val enableOrder: Boolean = false,
    val initCheckBox: (CheckBox.(T) -> Unit) = { visibility = View.GONE },
    var initEditButton: (ImageButton.(T) -> Unit) = { visibility = View.GONE },
    var initSettingsButton: (ImageButton.(T) -> Unit) = { visibility = View.GONE }
) :
    RecyclerView.Adapter<DynamicListAdapter<T>.ViewHolder>() {

        private val _entries = initialEntries.toMutableList()

    val entries: List<T>
        get() = _entries

    var multiselect = false
        private set

    private val selected = mutableListOf<T>()

    private var listener : OnItemChangedListener<T>? = null

    fun addOnItemChangedListener(x: OnItemChangedListener<T>) {
        listener = listener?.let { OnItemChangedListener.merge(it, x) } ?: x
    }

    private var onBackPressedCallback: OnBackPressedCallback? = null

    private var mainViewModel: MainViewModel? = null

    fun setViewModel(model: MainViewModel) {
        mainViewModel = model
    }

    inner class ViewHolder(entryUi: DynamicListEntryUi) : RecyclerView.ViewHolder(entryUi.root) {
        val multiselectCheckBox = entryUi.multiselectCheckBox
        val handleImage = entryUi.handleImage
        val checkBox = entryUi.checkBox
        val nameText = entryUi.nameText
        val editButton = entryUi.editButton
        val settingsButton = entryUi.settingsButton
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(DynamicListEntryUi(parent.context))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    @SuppressLint("NotifyDataSetChanged")
    @CallSuper
    open fun enterMultiSelect(onBackPressedDispatcher: OnBackPressedDispatcher) {
        mainViewModel?.let {
            if (multiselect)
                return
            onBackPressedCallback = object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    exitMultiSelect()
                }
            }
            onBackPressedDispatcher.addCallback(onBackPressedCallback!!)
            it.enableToolbarDeleteButton {
                deleteSelected()
                exitMultiSelect()
            }
            it.hideToolbarEditButton()
            multiselect = true
            notifyDataSetChanged()
        }
    }


    private fun deleteSelected() {
        if (!multiselect || selected.isEmpty())
            return
        val indexed = selected.mapNotNull { entry ->
            indexItem(entry).takeIf { it != -1 }?.let { it to entry }
        }.sortedByDescending { it.first }
        indexed.forEach { (index, _) ->
            _entries.removeAt(index)
            notifyItemRemoved(index)
        }
        listener?.onItemRemovedBatch(indexed)
    }

    @SuppressLint("NotifyDataSetChanged")
    @CallSuper
    open fun exitMultiSelect() {
        mainViewModel?.let {
            if (!multiselect)
                return
            onBackPressedCallback?.remove()
            it.disableToolbarDeleteButton()
            multiselect = false
            selected.clear()
            notifyDataSetChanged()
            if (entries.isNotEmpty())
                it.showToolbarEditButton()
        }
    }

    override fun getItemCount(): Int = entries.size

    @CallSuper
    open fun updateItem(idx: Int, item: T) {
        val old = _entries[idx]
        _entries[idx] = item
        notifyItemChanged(idx)
        listener?.onItemUpdated(idx, old, item)
    }

    fun indexItem(item: T): Int = _entries.indexOf(item)
}