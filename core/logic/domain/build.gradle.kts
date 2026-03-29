plugins {
    alias(libs.plugins.basket.kmpLibrary)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(project(":result"))
                implementation(project(":klogger"))
            }
        }
    }
}
