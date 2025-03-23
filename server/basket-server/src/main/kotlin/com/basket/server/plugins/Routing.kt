package com.basket.server.plugins

import com.basket.server.routes.authRoutes
import com.basket.server.routes.basketRoutes
import com.basket.server.routes.productRoutes
import com.basket.server.routes.storeRoutes
import com.basket.server.routes.userRoutes
import io.ktor.server.application.Application
import io.ktor.server.routing.routing

fun Application.configureRouting() {
    routing {
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
