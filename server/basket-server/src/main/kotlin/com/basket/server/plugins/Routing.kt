package com.basket.server.plugins

import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.basket.server.routes.*

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
