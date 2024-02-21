package com.manage.composesamples.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.manage.composesamples.ui.components.JetsnackScaffold
import com.manage.composesamples.ui.components.JetsnackSurface

@Composable
fun Feed(
    onSnackClick: (Long) -> Unit,
    onNavigateToRoute: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    JetsnackScaffold(
        modifier = modifier
    ) {paddingValues ->
        Feed(
            onSnackClick,
            Modifier.padding(paddingValues)
        )
    }
}

@Composable
private fun Feed(
    onSnackClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    JetsnackSurface(modifier = modifier.fillMaxSize()) {
        Box {
            DestinationBar()
        }
    }
}