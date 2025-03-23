package com.basket.sample.scango.presentation.core.platform

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
