package com.manage.composesamples.ui.home

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.manage.composesamples.R
import com.manage.composesamples.ui.home.cart.Cart
import com.manage.composesamples.ui.home.search.Search


fun NavGraphBuilder.addHomeGraph(
    onSnackSelected: (Long, NavBackStackEntry) -> Unit,
    onNavigateToRoute: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    composable(HomeSections.FEED.route) { from ->
        Feed(onSnackClick = { id -> onSnackSelected(id, from) }, onNavigateToRoute, modifier)
    }
    composable(HomeSections.SEARCH.route) { from ->
        Search(onSnackClick = { id -> onSnackSelected(id, from) }, onNavigateToRoute, modifier)
    }
    composable(HomeSections.CART.route) { from ->
        Cart(onSnackClick = { id -> onSnackSelected(id, from) }, onNavigateToRoute, modifier)
    }
    composable(HomeSections.PROFILE.route) {
        Profile(onNavigateToRoute, modifier)
    }
}

enum class HomeSections(
    @StringRes val title: Int,
    val icon: ImageVector,
    val route: String
) {
    FEED(R.string.home_feed, Icons.Outlined.Home, "home/feed"),
    SEARCH(R.string.home_search, Icons.Outlined.Search, "home/search"),
    CART(R.string.home_cart, Icons.Outlined.ShoppingCart, "home/cart"),
    PROFILE(R.string.home_profile, Icons.Outlined.AccountCircle, "home/profile")
}