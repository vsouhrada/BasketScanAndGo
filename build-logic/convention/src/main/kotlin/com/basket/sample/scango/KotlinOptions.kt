package com.basket.sample.scango

import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.KotlinCommonCompilerOptions
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompilerOptions

internal fun Project.warningsAsErrors(): Boolean =
    providers.gradleProperty("warningsAsErrors").map(String::toBoolean).orElse(false).get()

@OptIn(ExperimentalKotlinGradlePluginApi::class)
internal fun KotlinCommonCompilerOptions.applyStandardOptions(project: Project) {
    allWarningsAsErrors.set(project.warningsAsErrors())
    freeCompilerArgs.addAll(
        "-opt-in=kotlin.RequiresOptIn",
        "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
        "-opt-in=kotlinx.coroutines.FlowPreview",
    )
}

@OptIn(ExperimentalKotlinGradlePluginApi::class)
internal fun KotlinJvmCompilerOptions.setJvmTarget17() {
    jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
}
