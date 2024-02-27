package com.manage.videoeditor.android.composeui

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.manage.videoeditor.app.FeedAction
import com.manage.videoeditor.app.FeedStore
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainScreen : Screen, KoinComponent {
    @Composable
    override fun Content() {
        val store: FeedStore by inject()
        val context = LocalContext.current
        val navigator = LocalNavigator.currentOrThrow
        val state by store.observeState().collectAsState()
        val refreshState = rememberPullRefreshState(
            refreshing = state.progress,
            onRefresh = { store.dispatch(FeedAction.Refresh(true)) })

        LaunchedEffect(Unit) {
            store.dispatch(FeedAction.Refresh(false))
        }
        Box(modifier = Modifier.pullRefresh(refreshState)) {
            MainF
        }
    }
}