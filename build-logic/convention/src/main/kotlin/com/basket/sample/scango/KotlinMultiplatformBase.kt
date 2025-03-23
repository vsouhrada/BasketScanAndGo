package com.basket.sample.scango

import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun Project.configureKotlinMultiplatformBase(
    extension: KotlinMultiplatformExtension
) = extension.apply {

    jvmToolchain(17)

    jvm {
        withJava()
        testRuns.named("test") {
            executionTask.configure {
                useJUnitPlatform()
            }
        }
    }

    /*jvm("desktop") {
        testRuns.named("test") {
            executionTask.configure {
                useJUnitPlatform()
            }
        }
    }*/

    wasmJs { browser() }

    listOf(iosArm64(), iosSimulatorArm64())

    applyDefaultHierarchyTemplate()

    sourceSets.apply {
        all {
            languageSettings.apply {
                optIn("kotlin.contracts.ExperimentalContracts")
                optIn("kotlin.RequiresOptIn")
                optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
                optIn("kotlin.experimental.ExperimentalObjCName")
            }
        }

        commonMain {
            dependencies {
                implementation(libs.findLibrary("kotlinx.serialization.json").get())
                implementation(libs.findLibrary("kotlinx.datetime").get())
                implementation(libs.findLibrary("kotlinx.coroutines.core").get())
            }
        }


        commonTest {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        jvmTest {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }

        jsTest {
            dependencies {
                implementation(kotlin("test-js"))
                implementation(npm("@js-joda/timezone", "2.3.0"))
            }
        }
    }
}
