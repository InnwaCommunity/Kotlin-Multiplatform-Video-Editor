package com.manage.videoeditor.ui.components

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import com.manage.videoeditor.ui.navigation.NavigationItem
import com.manage.videoeditor.ui.theme.Gray
import com.manage.videoeditor.ui.theme.PrimaryColor
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun NavRailBar(
    modifier: Modifier = Modifier,
    navigator: Navigator,
    navigationItems: List<NavigationItem>
) {
    NavigationRail(
        modifier = modifier.fillMaxHeight().alpha(0.95F),
        contentColor = MaterialTheme.colorScheme.surface,
        header = {},
        containerColor = PrimaryColor
    ) {
        navigationItems.forEach { item ->
            val currentDestination = navigator.currentEntry.collectAsState(null).value?.route?.route
            val isSelected = item.route == currentDestination

            NavigationRailItem(
                icon = {
                    item.icon?.let {
                        Icon(
                            imageVector = it,
                            contentDescription = item.title
                        )
                    }
                },
                label = { Text(text = item.title) },
                colors = NavigationRailItemDefaults.colors(
                    selectedIconColor = PrimaryColor,
                    unselectedIconColor = Gray
                ),
                alwaysShowLabel = false,
                selected = isSelected,
                onClick = {
                    if (item.route != currentDestination) navigator.navigate(route = item.route)
                }
            )
        }
    }
}