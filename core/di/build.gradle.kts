plugins {
    alias(libs.plugins.basket.kotlinMultiplatform)
}

kotlin {

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
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
                implementation(project(":presentation"))
                implementation(project(":ktime"))
                implementation(project(":data"))
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

        wasmJsMain {
            dependencies { }
        }

        desktopMain {
            dependencies {}
        }
    }
}
