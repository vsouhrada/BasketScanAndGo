plugins {
    alias(libs.plugins.basket.appDesktop)
}

kotlin {
    sourceSets {
        val desktopMain by getting {
            dependencies {
                implementation(projects.apps.shared)
                implementation(libs.kotlinx.coroutines.swing)
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "com.basket.sample.scango.DesktopAppKt"

        nativeDistributions {
            packageName = "Basket Scan-Go"
            packageVersion = "1.0.0"

            macOS {
                bundleID = "com.basket.sample.scango"
                dockName = "Basket Scan&Go"
            }
        }
    }
}
