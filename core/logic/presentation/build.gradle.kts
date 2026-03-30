plugins {
    alias(libs.plugins.basket.kmpCompose)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(project(":domain"))
                api(project(":data"))
                api(project(":designSystem"))
                api(project(":klogger"))
                implementation(libs.androidx.lifecycle.viewmodel)
                implementation(libs.koin.compose.viewModel)
                implementation(libs.compose.navigation)
                implementation(libs.androidx.lifecycle.runtime.compose)
            }
        }
    }
}
