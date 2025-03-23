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
            baseName = "result"
            isStatic = true
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                api(project(":ktime"))
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
