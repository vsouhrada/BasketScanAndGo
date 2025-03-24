package com.basket.server.plugins

import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.cors.routing.CORS

fun Application.configureHTTP() {
    install(CORS) {
        // Allow specific origin instead of any host
        anyHost()
        allowHeadersPrefixed("X-")
        allowHeadersPrefixed("x-")
        allowHost("localhost:8081")
        allowHost("localhost:8080")
        allowHost("0.0.0.0:8081")
        allowHost("0.0.0.0:8080")
        allowHost("127.0.0.1:8081")
        allowHost("127.0.0.1:8080")
        allowHost("192.168.1.100:8080")
        allowHost("192.168.1.129:8080")
        allowHost("192.168.1.175:8080")
        // Allow Android emulator special IP
        allowHost("10.0.2.2:8080")
        allowHost("10.0.2.2:8081")

        // Allow methods
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Patch)

        // Allow headers
        allowHeader(HttpHeaders.Allow)
        allowHeader(HttpHeaders.Authorization)
        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.Accept)
        allowHeader(HttpHeaders.AccessControlAllowOrigin)
        allowHeader(HttpHeaders.Origin)

        // Allow credentials
        allowCredentials = true

        // Explicitly set the headers to be exposed in the response
        exposeHeader(HttpHeaders.AccessControlAllowOrigin)
        exposeHeader(HttpHeaders.AccessControlAllowMethods)
        exposeHeader(HttpHeaders.AccessControlAllowHeaders)
        exposeHeader(HttpHeaders.AccessControlAllowCredentials)
    }
}
