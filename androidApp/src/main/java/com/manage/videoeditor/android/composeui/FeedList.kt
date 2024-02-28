package com.manage.videoeditor.android.composeui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.manage.videoeditor.app.FeedStore
import com.manage.videoeditor.core.entity.Feed

@Composable
fun FeedList(store: FeedStore) {
    Box(modifier = Modifier.fillMaxSize()) {
        val state = store.observeState().collectAsState()
        val showAddDialog = remember {
            mutableSetOf(false)
        }
        val feedForDelete = remember<MutableState<Feed?>> {
            mutableStateOf(null)
        }
        FeedItem
    }
}