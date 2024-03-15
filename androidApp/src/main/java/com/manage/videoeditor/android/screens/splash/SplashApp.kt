package com.manage.videoeditor.android.screens.splash

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.manage.videoeditor.android.R
import com.manage.videoeditor.android.theme.JetsnackTheme

@Composable
fun SplashApp(navController: NavHostController) {
    JetsnackTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFC2CEB2)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painterResource(R.drawable.logo),
                contentDescription = "null"
            )
        }
    }
}

@Preview("default")
@Composable
private fun SnackDetailPreview() {
    val navController = rememberNavController()
    SplashApp(navController = navController)
}