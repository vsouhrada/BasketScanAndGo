package com.basket.sample.scango

import org.gradle.api.Project
import org.jetbrains.compose.ComposePlugin

internal fun Project.composeDependencies(): ComposePlugin.Dependencies =
    ComposePlugin.Dependencies(this)
