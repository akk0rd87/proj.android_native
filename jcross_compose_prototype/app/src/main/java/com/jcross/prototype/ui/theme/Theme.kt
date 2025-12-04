package com.jcross.prototype.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import com.jcross.prototype.model.AppTheme

// CompositionLocal for current theme
val LocalAppTheme = compositionLocalOf<AppTheme?> { null }

private val DarkColorScheme = darkColorScheme(
    primary = Primary,
    secondary = Secondary,
    background = BackgroundDark,
    surface = SurfaceDark,
    onPrimary = Surface,
    onSecondary = Surface,
    onBackground = Surface,
    onSurface = Surface
)

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    secondary = Secondary,
    background = BackgroundLight,
    surface = Surface,
    onPrimary = Surface,
    onSecondary = Surface,
    onBackground = TextPrimary,
    onSurface = TextPrimary
)

@Composable
fun JCrossTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    appTheme: AppTheme? = null,
    content: @Composable () -> Unit
) {
    val colorScheme = if (appTheme != null) {
        // Use custom theme colors
        lightColorScheme(
            primary = Color(appTheme.primaryColor),
            secondary = Color(appTheme.secondaryColor),
            background = Color(appTheme.backgroundColor),
            surface = Surface,
            onPrimary = Color.White,
            onSecondary = Color.White,
            onBackground = TextPrimary,
            onSurface = TextPrimary
        )
    } else if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    CompositionLocalProvider(LocalAppTheme provides appTheme) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}
