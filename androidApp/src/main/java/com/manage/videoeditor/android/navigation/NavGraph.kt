package com.manage.videoeditor.android.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.manage.videoeditor.android.screens.splash.SplashApp
import com.manage.videoeditor.android.theme.JetsnackTheme

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        composable(route = Screen.Splash.route) {
            SplashApp(navController = navController)
        }

        composable(route = Screen.Home.route) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(JetsnackTheme.colors.uiBackground),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Main Screen")
            }
        }
    }
}