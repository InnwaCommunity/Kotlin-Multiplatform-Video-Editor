package com.manage.videoeditor.android.composeui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.manage.videoeditor.app.FeedStore
import com.manage.videoeditor.core.entity.Post

@Composable
fun MainFeed(
    store: FeedStore,
    onPostClick: (Post) -> Unit,
    onEditClick: () -> Unit
) {
    val state = store.observeState().collectAsState()
}