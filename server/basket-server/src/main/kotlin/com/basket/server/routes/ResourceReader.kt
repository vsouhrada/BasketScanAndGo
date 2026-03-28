package com.basket.server.routes

internal fun readResourceText(resourcePath: String): String {
    val inputStream = Thread.currentThread().contextClassLoader.getResourceAsStream(resourcePath)
        ?: throw IllegalStateException("Resource not found: $resourcePath")

    return inputStream.bufferedReader().use { it.readText() }
}
