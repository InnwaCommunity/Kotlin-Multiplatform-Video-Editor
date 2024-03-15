package com.manage.videoeditor.android.screens.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.manage.videoeditor.android.theme.JetsnackTheme

@Composable
fun SplashApp(navController: NavHostController) {
    JetsnackTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(JetsnackTheme.colors.uiBackground),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Splash Screen")
        }
    }
}