package com.basket.sample.scango

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

internal val Project.libs: VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

internal val VersionCatalog.androidCompileSdk: Int
    get() = findVersion("android-compileSdk").get().requiredVersion.toInt()

internal val VersionCatalog.androidMinSdk: Int
    get() = findVersion("android-minSdk").get().requiredVersion.toInt()

internal val VersionCatalog.androidTargetSdk: Int
    get() = findVersion("android-targetSdk").get().requiredVersion.toInt()

internal fun VersionCatalog.pluginId(alias: String): String =
    findPlugin(alias).get().get().pluginId

internal fun Project.deriveNamespace(): String {
    val explicit = findProperty("basketNamespace") as? String
    if (!explicit.isNullOrBlank()) return explicit

    val segments = path.trim(':').split(":").toMutableList()
    if (segments.size > 1 && segments[0] == "core" && segments[1] == "logic") {
        segments.removeAt(1)
    }

    val cleaned = segments
        .filter { it.isNotBlank() }
        .joinToString(".") { it.replace("-", ".") }

    return listOf("com.basket.sample.scango", cleaned)
        .filter { it.isNotBlank() }
        .joinToString(".")
}
