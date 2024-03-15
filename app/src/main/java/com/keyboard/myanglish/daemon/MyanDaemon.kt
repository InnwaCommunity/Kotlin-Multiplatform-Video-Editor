package com.keyboard.myanglish.daemon

import com.keyboard.myanglish.core.Myan
import com.keyboard.myanglish.utils.appContext

object MyanDaemon {

    private val realMyan by lazy { Myan(appContext) }

    private val clients = mutableMapOf<String, MyanConnection>()

    fun getFirstConnectionOrNull() = clients.firstNotNullOfOrNull { it.value }

    fun stopMyan() {
        realMyan.stop()
    }
}