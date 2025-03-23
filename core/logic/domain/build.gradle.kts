plugins {
    alias(libs.plugins.basket.kotlinMultiplatformBase)
}

kotlin {

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "domain"
            isStatic = true
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                api(project(":result"))
                implementation(project(":klogger"))
            }
        }
    }
}
