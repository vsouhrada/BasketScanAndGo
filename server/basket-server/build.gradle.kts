plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    application
    id("io.ktor.plugin") version "3.4.1"
}

group = "com.basket.server"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Ktor server
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.serialization.json)
    implementation(libs.ktor.server.auth)
    implementation(libs.ktor.server.auth.jwt)
    implementation(libs.ktor.server.cors)
    implementation(libs.ktor.server.call.logging)
    implementation(libs.ktor.server.status.pages)

    // Logging
    implementation("ch.qos.logback:logback-classic:1.5.12")

    // Kotlin serialization
    implementation(libs.kotlinx.serialization.json)

    // Kotlin coroutines
    implementation(libs.kotlinx.coroutines.core)

    // Kotlin datetime
    implementation(libs.kotlinx.datetime)

    // JWT
    implementation("com.auth0:java-jwt:4.4.0")

    // Testing
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotlin.test)
}

application {
    mainClass.set("com.basket.server.ApplicationKt")
}

tasks.test {
    useJUnitPlatform()
}
