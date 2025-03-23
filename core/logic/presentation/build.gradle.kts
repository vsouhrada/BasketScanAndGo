plugins {
    alias(libs.plugins.basket.kotlinMultiplatform)
    alias(libs.plugins.basket.sharedWithCompose)
}


kotlin {
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "presentation"
            isStatic = true
        }
    }

    sourceSets {

        commonTest {
            dependencies {

            }
        }
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
              //  implementation(libs.compose.foundation)
            }
        }

        androidMain {
            dependencies {


            }
        }
        iosMain {
            dependencies {

            }
        }
    }
}


