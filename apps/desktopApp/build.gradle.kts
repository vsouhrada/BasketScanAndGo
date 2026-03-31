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

        jvmArgs += listOf(
            "-Xdock:name=Basket Scan&Go",
            "-Xdock:icon=${project.file("src/desktopMain/resources/basket_icon.png").absolutePath}",
        )

        nativeDistributions {
            packageName = "Basket Scan-Go"
            packageVersion = "1.0.0"

            macOS {
                bundleID = "com.basket.sample.scango"
                dockName = "Basket Scan&Go"
                iconFile.set(project.file("src/desktopMain/resources/basket_icon.icns"))
            }
        }
    }
}
