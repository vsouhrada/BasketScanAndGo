plugins {
    `kotlin-dsl`
}

group = "com.basket.sample.scango.buildlogic"

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${libs.versions.kotlin.get()}")
    implementation("org.jetbrains.kotlin:compose-compiler-gradle-plugin:${libs.versions.kotlin.get()}")
    implementation("com.android.tools.build:gradle:${libs.versions.agp.get()}")
    implementation("org.jetbrains.compose:compose-gradle-plugin:${libs.versions.compose.plugin.get()}")
    implementation(libs.plugins.kotlinMultiplatform.toDep())
    implementation(libs.plugins.androidApplication.toDep())
    implementation(libs.plugins.androidLibrary.toDep())
    implementation(libs.plugins.android.kotlin.multiplatform.library.toDep())
    implementation(libs.plugins.compose.compiler.toDep())
    implementation(libs.plugins.jetbrainsCompose.toDep())
    implementation(libs.plugins.kotlin.serialization.toDep())
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
        register("kmpLibrary") {
            id = "com.basket.sample.scango.kmp.library"
            implementationClass = "com.basket.sample.scango.KmpLibraryConventionPlugin"
        }
        register("kmpCompose") {
            id = "com.basket.sample.scango.kmp.compose"
            implementationClass = "com.basket.sample.scango.KmpComposeConventionPlugin"
        }
        register("androidApp") {
            id = "com.basket.sample.scango.app.android"
            implementationClass = "com.basket.sample.scango.AndroidAppConventionPlugin"
        }
        register("desktopApp") {
            id = "com.basket.sample.scango.app.desktop"
            implementationClass = "com.basket.sample.scango.DesktopAppConventionPlugin"
        }
        register("wasmApp") {
            id = "com.basket.sample.scango.app.wasm"
            implementationClass = "com.basket.sample.scango.WasmAppConventionPlugin"
        }
        register("iosApp") {
            id = "com.basket.sample.scango.app.ios"
            implementationClass = "com.basket.sample.scango.IosAppConventionPlugin"
        }
    }
}
