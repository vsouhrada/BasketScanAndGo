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
            baseName = "data"
            isStatic = true
            export(project(":domain"))
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                api(project(":domain"))
                implementation(project(":klogger"))
                api(libs.ktor.client.core)
                api(libs.ktor.client.content.negotiation)
                api(libs.ktor.serialization.json)
                api(libs.ktor.serialization.xml)
                api(libs.ktor.logging)
                api(libs.ktor.client.auth)
                api(libs.ktor.client.content.negotiation)
            }
        }

        androidMain {
            dependencies {
                api(libs.ktor.client.okhttp)
            }
        }

        jvmMain {
            dependencies {
                api(libs.ktor.client.okhttp)
            }
        }

        iosMain {
            dependencies {
                api(libs.ktor.client.ios)
            }
        }

        jsMain {
            dependencies {
                implementation(libs.ktor.client.js.js)
            }
        }

        wasmJsMain {
            dependencies {
                implementation(libs.ktor.client.js.wasm)
            }
        }

        desktopMain {
            dependencies {
                api(libs.ktor.client.okhttp)
            }
        }
    }
}
