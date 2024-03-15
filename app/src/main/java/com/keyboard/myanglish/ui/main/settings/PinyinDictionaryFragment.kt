package com.keyboard.myanglish.ui.main.settings

import androidx.fragment.app.Fragment

class PinyinDictionaryFragment : Fragment() {
    companion object {
        private var RELOAD_ID = 0
        private var IMPORT_ID = 0
        const val CHANNEL_ID = "pinyin_dict"
        const val INTENT_DATA_URI = "uri"
    }
}