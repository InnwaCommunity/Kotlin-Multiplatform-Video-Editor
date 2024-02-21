package com.manage.composesamples.ui.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.manage.composesamples.ui.components.JetsnackScaffold
import com.manage.composesamples.ui.components.JetsnackSurface
import com.manage.composesamples.ui.theme.JetsnackTheme

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

@Preview("default")
@Preview("dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("large font", fontScale = 2f)
@Composable
fun HomePreview() {
    JetsnackTheme {
        Feed(onSnackClick = {}, onNavigateToRoute = {})
    }
}