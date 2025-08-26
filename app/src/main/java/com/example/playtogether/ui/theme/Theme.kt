package com.example.playtogether.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Définis tes couleurs personnalisées
private val LightColorPalette = lightColors(
    primary = Color(0xFF1976D2),
    primaryVariant = Color(0xFF023805),
    secondary = Color(0xFFFF9800)
)

private val DarkColorPalette = darkColors(
    primary = Color(0xFF90CAF9),
    primaryVariant = Color(0xFF023805),
    secondary = Color(0xFFFFB74D)
)

@Composable
fun PlayTogether(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorPalette else LightColorPalette

    MaterialTheme(
        colors = colors,
        content = content
    )
}

