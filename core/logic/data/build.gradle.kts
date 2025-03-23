plugins {
    alias(libs.plugins.basket.kotlinMultiplatform)
}

kotlin {

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
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
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.serialization.json)
                implementation(libs.ktor.serialization.xml)
                implementation(libs.ktor.logging)
                implementation(libs.ktor.client.auth)
                implementation(libs.ktor.client.content.negotiation)
            }
        }
    }
}


