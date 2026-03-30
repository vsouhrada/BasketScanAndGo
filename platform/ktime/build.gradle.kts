plugins {
    alias(libs.plugins.basket.kmpLibrary)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(libs.kotlinx.datetime)
            }
        }
    }
}
