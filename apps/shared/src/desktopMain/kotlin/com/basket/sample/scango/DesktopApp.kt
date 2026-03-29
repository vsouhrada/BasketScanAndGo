package com.basket.sample.scango

import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Basket Scan&Go",
        state =
        rememberWindowState(
            position = WindowPosition.Aligned(Alignment.Center),
            width = 1200.dp,
            height = 700.dp,
        ),
    ) {
        App()
    }
}
