package com.manage.videoeditor

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@Composable
fun ChatAppWithScaffold(displayTextField: Boolean = true) {
    Theme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("The Composers Chat") },
                    backgroundColor = MaterialTheme.colors.background
                )
            }
        ) {
            ChatApp(displayTextField = displayTextField)
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ChatApp(displayTextField: Boolean = true) {
    Theme {
        Surface {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painterResource(DrawableResource("background.jpg")),
                    null,
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Composable
fun Theme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = lightColors(
            surface = Color(ChatColors.SURFACE),
            background = Color(ChatColors.TOP_GRADIENT.last())
        )
    ) {
        ProvideTextStyle(LocalTextStyle.current.copy(letterSpacing = 0.sp)) {
            content()
        }
    }
}