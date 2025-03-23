package com.basket.core.common.designSystem.compose.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults.textFieldColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.basket.core.common.designSystem.compose.theme.ColorPalette.AccentColor
import com.basket.core.common.designSystem.compose.theme.ColorPalette.PrimaryColor
import com.basket.core.common.designSystem.compose.theme.ColorPalette.PrimaryVariantColor
import com.basket.core.common.designSystem.compose.theme.ColorPalette.TextFieldColor
import com.basket.core.common.designSystem.compose.theme.ColorPalette.lightSurface

private val darkColorPalette =
    darkColorScheme(
        primary = PrimaryColor,
        primaryContainer = PrimaryVariantColor,
        secondary = AccentColor,
    )

private val lightColorPalette =
    lightColorScheme(
        primary = PrimaryColor,
        primaryContainer = PrimaryVariantColor,
        secondary = AccentColor,
        background = Color.White,
        surfaceVariant = Color.White,
        surface = lightSurface,
    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
     */
    )

@Composable
fun BasketTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit,) {
    val colors =
        if (darkTheme) {
            darkColorPalette
        } else {
            lightColorPalette
        }

    MaterialTheme(
        colorScheme = lightColorPalette,
        typography = BasketTypography(),
        shapes = shapes,
        content = content,
    )
}

@Composable
fun DefaultNavigationBarItemTheme() = NavigationBarItemDefaults.colors(
    selectedIconColor = MaterialTheme.colorScheme.primary,
    unselectedIconColor = MaterialTheme.colorScheme.primary,
    unselectedTextColor = MaterialTheme.colorScheme.primary.copy(.7f),
    selectedTextColor = MaterialTheme.colorScheme.primary,
    indicatorColor = MaterialTheme.colorScheme.background,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultTextFieldTheme() = textFieldColors(
    focusedContainerColor = TextFieldColor,
    unfocusedContainerColor = TextFieldColor,
    cursorColor = MaterialTheme.colorScheme.onBackground,
    focusedIndicatorColor = Color.Transparent,
    unfocusedIndicatorColor = Color.Transparent,
    disabledContainerColor = TextFieldColor,
    disabledTextColor = MaterialTheme.colorScheme.onBackground,
    disabledIndicatorColor = Color.Transparent,
)

@Composable
fun DefaultImageButtonTheme() = buttonColors(
    containerColor = MaterialTheme.colorScheme.background,
    contentColor = MaterialTheme.colorScheme.primary,
    // disabledBackgroundColor = MaterialTheme.colorScheme.background,
    disabledContentColor = MaterialTheme.colorScheme.primary,
)

@Composable
fun DefaultButtonTheme() = buttonColors(
    containerColor = MaterialTheme.colorScheme.primary,
    contentColor = MaterialTheme.colorScheme.background,
    // disabledBackgroundColor = MaterialTheme.colorScheme.background,
    disabledContentColor = MaterialTheme.colorScheme.primary,
)

@Composable
fun DefaultButtonWithBorderPrimaryTheme() = buttonColors(
    containerColor = MaterialTheme.colorScheme.background,
    contentColor = MaterialTheme.colorScheme.primary,
    disabledContainerColor = MaterialTheme.colorScheme.background,
    // disabledBackgroundColor = MaterialTheme.colorScheme.background,
    disabledContentColor = MaterialTheme.colorScheme.primary,
)
