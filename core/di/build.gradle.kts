plugins {
    alias(libs.plugins.basket.kmpLibrary)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":presentation"))
                implementation(project(":ktime"))
                implementation(project(":data"))
                api(libs.koin.core)
                api(libs.koin.compose)
                api(libs.koin.compose.viewModel)
            }
        }
    }
}
