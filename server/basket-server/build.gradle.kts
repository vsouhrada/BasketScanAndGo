plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    application
    id("io.ktor.plugin") version "3.1.1"
}

group = "com.basket.server"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Ktor server
    implementation("io.ktor:ktor-server-core:3.1.1")
    implementation("io.ktor:ktor-server-netty:3.1.1")
    implementation("io.ktor:ktor-server-content-negotiation:3.1.1")
    implementation("io.ktor:ktor-serialization-kotlinx-json:3.1.1")
    implementation("io.ktor:ktor-server-auth:3.1.1")
    implementation("io.ktor:ktor-server-auth-jwt:3.1.1")
    implementation("io.ktor:ktor-server-cors:3.1.1")
    implementation("io.ktor:ktor-server-call-logging:3.1.1")
    implementation("io.ktor:ktor-server-status-pages:3.1.1")

    // Logging
    implementation("ch.qos.logback:logback-classic:1.5.3")

    // Kotlin serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.0")

    // Kotlin coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")

    // Kotlin datetime
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.5.0")

    // JWT
    implementation("com.auth0:java-jwt:4.4.0")

    // Testing
    testImplementation("io.ktor:ktor-server-test-host:3.1.1")
    testImplementation("org.jetbrains.kotlin:kotlin-test:2.1.10")
}

application {
    mainClass.set("com.basket.server.ApplicationKt")
}

tasks.test {
    useJUnitPlatform()
}
