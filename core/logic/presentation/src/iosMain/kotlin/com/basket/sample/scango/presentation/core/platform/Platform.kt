package com.basket.sample.scango.presentation.core.platform

import platform.UIKit.UIDevice
import kotlin.native.Platform

class IOSPlatform : Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()
