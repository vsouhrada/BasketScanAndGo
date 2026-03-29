package com.basket.sample.scango

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class WasmAppConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(libs.pluginId("kotlinMultiplatform"))
            apply(libs.pluginId("jetbrainsCompose"))
            apply(libs.pluginId("compose-compiler"))
        }

        val compose = composeDependencies()

        extensions.configure<ComposeCompilerGradlePluginExtension> {
            stabilityConfigurationFiles.set(
                listOf(layout.projectDirectory.file("stability.conf"))
            )
        }

        extensions.configure<KotlinMultiplatformExtension> {
            @OptIn(ExperimentalWasmDsl::class)
            wasmJs {
                browser()
                binaries.executable()
            }

            @OptIn(ExperimentalKotlinGradlePluginApi::class)
            compilerOptions {
                applyStandardOptions(this@with)
            }

            sourceSets.named("commonMain") {
                dependencies {
                    implementation(compose.runtime)
                    implementation(compose.foundation)
                    implementation(compose.material3)
                    implementation(compose.ui)
                    implementation(compose.components.resources)
                }
            }
        }
    }
}
