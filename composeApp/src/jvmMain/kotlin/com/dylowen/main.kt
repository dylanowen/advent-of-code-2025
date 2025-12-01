package com.dylowen

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Advent of Code - 2025",
    ) {
        App()
    }
}

