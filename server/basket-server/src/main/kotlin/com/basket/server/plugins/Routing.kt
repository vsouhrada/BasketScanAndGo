package com.basket.server.plugins

import com.basket.server.routes.authRoutes
import com.basket.server.routes.basketRoutes
import com.basket.server.routes.productRoutes
import com.basket.server.routes.storeRoutes
import com.basket.server.routes.userRoutes
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import java.time.Instant

fun Application.configureRouting() {
    routing {
        get("/health") {
            call.respond(
                HttpStatusCode.OK,
                mapOf(
                    "status" to "UP",
                    "timestamp" to Instant.now().toString(),
                ),
            )
        }

        // Authentication routes
        authRoutes()

        // Basket routes
        basketRoutes()

        // Product routes
        productRoutes()

        // Store routes
        storeRoutes()

        // User routes
        userRoutes()
    }
}
