plugins {
    alias(libs.plugins.basket.appWasm)
}

kotlin {
    sourceSets {
        val wasmJsMain by getting {
            dependencies {
                implementation(projects.apps.shared)
                implementation(libs.kotlinx.browser)
            }
        }
    }
}
