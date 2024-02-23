package com.manage.composesamples.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.navigation
import com.manage.composesamples.ui.home.HomeSections
import com.manage.composesamples.ui.home.addHomeGraph
import com.manage.composesamples.ui.navigation.MainDestinations
import com.manage.composesamples.ui.navigation.rememberJetsnackNavController
import com.manage.composesamples.ui.theme.JetsnackTheme

@Composable
fun JetsnackApp() {
    JetsnackTheme {
        val jetsnackNavController = rememberJetsnackNavController()
        NavHost(
            navController = jetsnackNavController.navController,
            startDestination = MainDestinations.HOME_ROUTE
        ) {
            jetsnackNavGraph(
                onSnackSelected = jetsnackNavController::navigateToSnackDetail,
                upPress = jetsnackNavController::upPress,
                onNavigateToRoute = jetsnackNavController::navigateToBottomBarRoute
            )
        }
    }
}

private fun NavGraphBuilder.jetsnackNavGraph(
    onSnackSelected: (Long, NavBackStackEntry) -> Unit,
    upPress: () -> Unit,
    onNavigateToRoute: (String) -> Unit
) {
    navigation(
        route = MainDestinations.HOME_ROUTE,
        startDestination = HomeSections.FEED.route
    ) {
        addHomeGraph(onSnackSelected, onNavigateToRoute)
    }
}