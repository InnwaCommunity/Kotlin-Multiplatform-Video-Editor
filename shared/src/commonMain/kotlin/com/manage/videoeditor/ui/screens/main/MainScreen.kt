package com.manage.videoeditor.ui.screens.main

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.manage.videoeditor.ui.components.BottomNavBar
import com.manage.videoeditor.ui.components.NavRailBar
import com.manage.videoeditor.ui.navigation.NavigationItem
import com.manage.videoeditor.ui.theme.NotflixTheme
import com.manage.videoeditor.utils.WindowSize
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.rememberNavigator
import org.koin.compose.koinInject

@Composable
fun MainScreen(viewModel: MainViewModel = koinInject() ) {
    PreComposeApp {
        val appUiState = viewModel.mainUiState.collectAsState().value
        var windowSize by remember { mutableStateOf(WindowSize.COMPACT) }

        val isDarkTheme = appUiState.selectedTheme != 0

        NotflixTheme(darkTheme = isDarkTheme) {
            val navigator = rememberNavigator()

            val topLevelDestinations = listOf(
                NavigationItem.Home,
                NavigationItem.Favorites,
                NavigationItem.Settings
            )

            val isTopLevelDestination = navigator.currentEntry.collectAsState(null).value?.route?.route in topLevelDestinations.map { it.route }

            val showNavigationRail = windowSize != WindowSize.COMPACT

            Scaffold(
                bottomBar = {
                    if (isTopLevelDestination && !showNavigationRail) {
                        BottomNavBar(bottomNavItems = topLevelDestinations, navigator = navigator)
                    }
                }
            ) { paddingValues ->
                BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                    windowSize = WindowSize.basedOnWidth(this.minWidth)

                    Row(modifier = Modifier.fillMaxSize()) {
                        if(isTopLevelDestination && showNavigationRail) {
                            NavRailBar(
                                navigationItems = topLevelDestinations,
                                navigator = navigator
                            )
                        }

                    }
                }
            }
        }
    }
}