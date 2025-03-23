plugins {
    `kotlin-dsl`
}

group = "com.basket.sample.scango.buildlogic"

dependencies {
    compileOnly(libs.plugins.kotlin.serialization.toDep())
    compileOnly(libs.plugins.androidApplication.toDep())
    compileOnly(libs.plugins.androidLibrary.toDep())
    compileOnly(libs.plugins.jetbrainsCompose.toDep())
    compileOnly(libs.plugins.kotlinMultiplatform.toDep())
    compileOnly(libs.plugins.compose.compiler.toDep())
}

fun Provider<PluginDependency>.toDep() = map {
    "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version}"
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("kotlinMultiplatform") {
            id = "com.basket.sample.scango.kotlinMultiplatform"
            implementationClass = "KotlinMultiplatformConventionPlugin"
        }
        register("kotlinMultiplatformBase") {
            id = "com.basket.sample.scango.kotlinMultiplatformBase"
            implementationClass = "KotlinMultiplatformBaseConventionPlugin"
        }
        register("sharedWithCompose") {
            id = "com.basket.sample.scango.sharedWithCompose"
            implementationClass = "SharedWithComposeConventionPlugin"
        }
        register("androidApp") {
            id = "com.basket.sample.scango.androidApp"
            implementationClass = "AndroidAppConventionPlugin"
        }
    }
}
