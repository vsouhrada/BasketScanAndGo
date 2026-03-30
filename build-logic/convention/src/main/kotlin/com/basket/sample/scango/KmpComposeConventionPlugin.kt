package com.basket.sample.scango

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KmpComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply("com.basket.sample.scango.kmp.library")
        pluginManager.apply(libs.pluginId("jetbrainsCompose"))
        pluginManager.apply(libs.pluginId("compose-compiler"))

        val compose = composeDependencies()

        extensions.configure<ComposeCompilerGradlePluginExtension> {
            stabilityConfigurationFiles.set(
                listOf(layout.projectDirectory.file("stability.conf"))
            )
        }

        extensions.configure<KotlinMultiplatformExtension> {
            sourceSets.named("commonMain") {
                dependencies {
                    implementation(compose.runtime)
                    implementation(compose.foundation)
                    implementation(compose.material)
                    implementation(compose.material3)
                    implementation(compose.ui)
                    implementation(compose.components.resources)
                    implementation(compose.components.uiToolingPreview)
                    api(compose.materialIconsExtended)
                }
            }

            sourceSets.named("commonTest") {
                dependencies {
                    implementation(kotlin("test"))
                }
            }

            sourceSets.matching { it.name == "androidMain" }.all {
                dependencies {
                    implementation(libs.findLibrary("androidx-activity-compose").get())
                    implementation(libs.findLibrary("androidx-lifecycle-viewmodel-compose").get())
                    implementation(libs.findLibrary("androidx-lifecycle-runtime-compose").get())
                    implementation(libs.findLibrary("compose-ui-tooling-preview").get())
                }
            }

            sourceSets.matching { it.name == "desktopMain" }.all {
                dependencies {
                    implementation(composeDependencies().desktop.currentOs)
                }
            }
        }
    }
}
