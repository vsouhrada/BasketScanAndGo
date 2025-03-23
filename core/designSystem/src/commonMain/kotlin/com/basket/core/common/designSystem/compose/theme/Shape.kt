package com.basket.core.common.designSystem.compose.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val shapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(24.dp),
    extraLarge = RoundedCornerShape(32.dp),
)

object Spacing {
    val spacing2 = 2.dp
    val spacing4 = 4.dp
    val spacing8 = 8.dp
    val spacing16 = 16.dp
}
