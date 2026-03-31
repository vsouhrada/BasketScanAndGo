package com.basket.sample.scango

import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import org.jetbrains.skia.Image
import java.awt.Taskbar
import javax.imageio.ImageIO

private fun loadAppIcon() = BitmapPainter(
    Image.makeFromEncoded(
        Thread.currentThread().contextClassLoader
            .getResourceAsStream("basket_icon.png")!!
            .readBytes()
    ).toComposeImageBitmap()
)

private fun setDockIcon() {
    try {
        val iconStream = Thread.currentThread().contextClassLoader
            .getResourceAsStream("basket_icon.png") ?: return
        val image = ImageIO.read(iconStream)
        if (Taskbar.isTaskbarSupported()) {
            val taskbar = Taskbar.getTaskbar()
            taskbar.iconImage = image
        }
    } catch (_: Exception) {
        // Silently ignore on platforms that don't support Taskbar
    }
}

fun main() {
    setDockIcon()
    application {
        val icon = loadAppIcon()
        Window(
            onCloseRequest = ::exitApplication,
            title = "Basket Scan&Go",
            icon = icon,
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
}
