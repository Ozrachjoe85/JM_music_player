package com.jm.musicplayer.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val VoidColorScheme = darkColorScheme(
    primary = VoidCyan,
    secondary = VoidPurple,
    tertiary = VoidPink,
    background = VoidBlack,
    surface = VoidBlack,
    onPrimary = VoidBlack,
    onSecondary = VoidBlack,
    onBackground = VoidCyan,
    onSurface = VoidCyan
)

@Composable
fun JMMusicPlayerTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = VoidColorScheme,
        typography = Typography, // We will define this in the next step
        content = content
    )
}
