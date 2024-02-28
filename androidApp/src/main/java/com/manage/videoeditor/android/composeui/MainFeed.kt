package com.manage.videoeditor.android.composeui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.manage.videoeditor.app.FeedStore
import com.manage.videoeditor.core.entity.Post

@Composable
fun MainFeed(
    store: FeedStore,
    onPostClick: (Post) -> Unit,
    onEditClick: () -> Unit
) {
    val state = store.observeState().collectAsState()
    val posts = remember(state.value.feeds, state.value.selectedFeed) {
        (state.value.selectedFeed?.posts ?: state.value.feeds.flatMap { it.posts })
            .sortedByDescending { it.date }
    }
    Column {
        val coroutineScope = rememberCoroutineScope()
        val listState = rememberLazyListState()
        PolyList(
            modifier = Modifier.weight(1f),
            posts = posts,
            listState = listState
        ) { post -> onPostClick(post) }
        Spacer(
            Modifier
                .windowInsetsBottomHeight(WindowInsets.navigationBars)
                .fillMaxWidth()
        )
    }
}