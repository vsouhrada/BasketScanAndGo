plugins {
    alias(libs.plugins.basket.kmpLibrary)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(project(":ktime"))
                api(libs.kotlinx.datetime)
            }
        }
    }
}
