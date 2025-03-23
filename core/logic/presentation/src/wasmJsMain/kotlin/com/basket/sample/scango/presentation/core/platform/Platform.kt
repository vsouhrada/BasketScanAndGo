package com.basket.sample.scango.presentation.core.platform

class WasmPlatform : Platform {
    override val name: String = "Web Kotlin/Wasm"
}

actual fun getPlatform(): Platform = WasmPlatform()
