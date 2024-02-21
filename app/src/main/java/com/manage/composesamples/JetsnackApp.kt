package com.manage.composesamples

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCompositionContext
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.manage.composesamples.model.Snack
import com.manage.composesamples.ui.home.HomeSections
import com.manage.composesamples.ui.home.addHomeGraph
import com.manage.composesamples.ui.navigation.MainDestinations
import com.manage.composesamples.ui.navigation.rememberJetsnackNavController
import com.manage.composesamples.ui.snackdetail.SnackDetail
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
    composable(
        "${MainDestinations.SNACK_DETAIL_ROUTE}/{${MainDestinations.SNACK_ID_KEY}}",
        arguments = listOf(navArgument(MainDestinations.SNACK_ID_KEY) { type = NavType.LongType })
    ) { backStackEntry ->
        val arguments = requireNotNull(backStackEntry.arguments)
        val snackId = arguments.getLong(MainDestinations.SNACK_ID_KEY)
        SnackDetail(snackId, upPress)
    }
}
