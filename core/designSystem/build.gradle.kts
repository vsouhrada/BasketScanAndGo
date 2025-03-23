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


