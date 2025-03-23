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
            baseName = "ktime"
            isStatic = true
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                api(libs.kotlinx.datetime)
            }
        }

        jvmMain {
            dependencies {}
        }

        jvmTest {
            dependencies {}
        }
    }
}
