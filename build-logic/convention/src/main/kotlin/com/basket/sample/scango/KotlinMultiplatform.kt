package com.basket.sample.scango

import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

internal fun Project.configureKotlinMultiplatform(
    extension: KotlinMultiplatformExtension
) = extension.apply {

    jvmToolchain(17)

    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    jvm("desktop")

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs { browser() }

    listOf(iosArm64(), iosSimulatorArm64())

    applyDefaultHierarchyTemplate()

    sourceSets.apply {

        commonMain {
            dependencies {
                implementation(libs.findLibrary("kotlinx.serialization.json").get())
                implementation(libs.findLibrary("kotlinx.datetime").get())
                implementation(libs.findLibrary("kotlinx.coroutines.core").get())
                api(libs.findLibrary("koin.core").get())
            }
        }

        androidMain {
            dependencies {
                // implementation(libs.findLibrary("koin.android").get())
                // implementation(libs.findLibrary("kotlinx.coroutines.android").get())
            }
        }
    }
}
