package com.jcross.prototype.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Two Dots inspired pastel colors for size categories
val ColorXS = Color(0xFFFFB84D)  // Warm Peach/Orange
val ColorS = Color(0xFF7DD181)   // Soft Mint Green
val ColorM = Color(0xFFB399D8)   // Lavender Purple
val ColorL = Color(0xFF6BBBF5)   // Sky Blue
val ColorXL = Color(0xFFFF8FB1)  // Coral Pink

// Soft pastel background colors (very light tints)
val ColorXSLight = Color(0xFFFFF5E6)  // Very light peach
val ColorSLight = Color(0xFFEEF9EF)   // Very light mint
val ColorMLight = Color(0xFFF5F0FB)   // Very light lavender
val ColorLLight = Color(0xFFEEF7FD)   // Very light sky
val ColorXLLight = Color(0xFFFFEEF4)  // Very light pink

// App theme colors - Two Dots style
val BackgroundLight = Color(0xFFF8FAFB)  // Very light cool gray
val BackgroundDark = Color(0xFF1A1A1A)
val Surface = Color(0xFFFFFFFF)
val SurfaceDark = Color(0xFF2C2C2C)

// Accent colors in Two Dots style
val Primary = Color(0xFF6BBBF5)     // Sky blue
val PrimaryVariant = Color(0xFF5AA3E0)
val Secondary = Color(0xFFFFB84D)   // Warm peach

// Text colors - softer and more muted
val TextPrimary = Color(0xFF4A5568)    // Soft dark gray
val TextSecondary = Color(0xFF9CA3AF)  // Medium gray
val TextDisabled = Color(0xFFD1D5DB)   // Light gray

val DividerColor = Color(0xFFE5E7EB)
val ShadowColor = Color(0x0A000000)  // Very subtle shadow

// Two Dots style gradient backgrounds
val BackgroundGradient = Brush.verticalGradient(
    colors = listOf(
        Color(0xFFF0F9FF),  // Very light blue at top
        Color(0xFFFFF5F7),  // Very light pink at bottom
    )
)
