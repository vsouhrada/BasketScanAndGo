package com.basket.sample.scango

import com.android.build.api.variant.KotlinMultiplatformAndroidComponentsExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.targets.jvm.KotlinJvmTarget

class KmpLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(libs.pluginId("kotlinMultiplatform"))
            apply(libs.pluginId("kotlin-serialization"))
            apply(libs.pluginId("android-kotlin-multiplatform-library"))
        }

        extensions.configure<KotlinMultiplatformExtension> {
            jvmToolchain(17)
            applyDefaultHierarchyTemplate()

            jvm("desktop") {
                testRuns.named("test") {
                    executionTask.configure {
                        useJUnitPlatform()
                    }
                }
            }

            iosArm64()
            iosSimulatorArm64()

            targets.withType(KotlinNativeTarget::class.java).configureEach {
                binaries.framework {
                    baseName = project.path.substringAfterLast(":").replace("-", "")
                    isStatic = true
                }
            }

            @OptIn(ExperimentalWasmDsl::class)
            wasmJs {
                browser()
            }

            sourceSets.configureEach {
                languageSettings.optIn("kotlin.RequiresOptIn")
                languageSettings.optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
                languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
            }

            @OptIn(ExperimentalKotlinGradlePluginApi::class)
            compilerOptions {
                applyStandardOptions(this@with)
            }

            sourceSets.named("commonMain") {
                dependencies {
                    implementation(libs.findLibrary("kotlinx-coroutines-core").get())
                    implementation(libs.findLibrary("kotlinx-datetime").get())
                    implementation(libs.findLibrary("kotlinx-serialization-json").get())
                }
            }

            sourceSets.named("commonTest") {
                dependencies {
                    implementation(kotlin("test"))
                    implementation(libs.findLibrary("coroutines-test").get())
                }
            }

            targets.withType<KotlinJvmTarget> {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_17)
                }
            }
        }

        extensions.configure<KotlinMultiplatformAndroidComponentsExtension> {
            finalizeDsl { extension ->
                extension.compileSdk = libs.androidCompileSdk
                extension.minSdk = libs.androidMinSdk
                extension.namespace = deriveNamespace()
                extension.packaging.resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }
    }
}
