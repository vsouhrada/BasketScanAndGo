package com.basket.server

import com.basket.server.plugins.configureExceptionHandling
import com.basket.server.plugins.configureHTTP
import com.basket.server.plugins.configureMonitoring
import com.basket.server.plugins.configureRouting
import com.basket.server.plugins.configureSecurity
import com.basket.server.plugins.configureSerialization
import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureRouting()
    configureSerialization()
    configureSecurity()
    configureMonitoring()
    configureHTTP()
    configureExceptionHandling()
}
