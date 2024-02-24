package com.manage.composesamples.ui.components

import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.manage.composesamples.ui.theme.JetsnackTheme


@Composable
fun JetsnackDivider(
    modifier: Modifier = Modifier,
    color: Color = JetsnackTheme.colors.uiBackground,
    thickness: Dp = 1.dp,
    startIndent: Dp = 0.dp
) {
    Divider(
        modifier = modifier,
        color = color,
        thickness = thickness,
        startIndent = startIndent
    )
}
