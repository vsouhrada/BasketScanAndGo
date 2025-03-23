package com.basket.core.common.designSystem.compose.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

fun baseGradientBackground() = Brush.linearGradient(
    colors = listOf(
        ColorPalette.blue900,
        ColorPalette.blue400,
        ColorPalette.petrol800
    )
)

fun buttonGradientBackground() = Brush.linearGradient(
    listOf(
        Color(0xFF1C1C59),
        Color(0xFF34386D),
        Color(0xFFE60B41)
    )
)
