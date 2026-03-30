package com.basket.sample.scango

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

class AndroidAppConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(libs.pluginId("androidApplication"))
            apply(libs.pluginId("compose-compiler"))
        }

        extensions.configure<ApplicationExtension> {
            compileSdk = libs.androidCompileSdk
            namespace = "com.basket.sample.scango"

            defaultConfig {
                applicationId = "com.basket.sample.scango"
                minSdk = libs.androidMinSdk
                targetSdk = libs.androidTargetSdk
                versionCode = 1
                versionName = "1.0.0"
            }

            buildFeatures.compose = true

            packaging.resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }
        }

        extensions.configure<ComposeCompilerGradlePluginExtension> {
            stabilityConfigurationFiles.set(
                listOf(layout.projectDirectory.file("stability.conf"))
            )
        }

        tasks.withType<KotlinJvmCompile>().configureEach {
            @OptIn(ExperimentalKotlinGradlePluginApi::class)
            compilerOptions {
                setJvmTarget17()
                applyStandardOptions(this@with)
            }
        }

        dependencies {
            add("implementation", libs.findLibrary("androidx-activity-compose").get())
            add("implementation", libs.findLibrary("androidx-lifecycle-viewmodel-compose").get())
            add("implementation", libs.findLibrary("androidx-lifecycle-runtime-compose").get())
            add("implementation", libs.findLibrary("compose-material3").get())
            add("implementation", libs.findLibrary("compose-ui").get())
            add("implementation", libs.findLibrary("compose-foundation").get())
            add("implementation", libs.findLibrary("compose-ui-tooling-preview").get())
            add("debugImplementation", libs.findLibrary("compose-ui-tooling").get())
        }
    }
}
