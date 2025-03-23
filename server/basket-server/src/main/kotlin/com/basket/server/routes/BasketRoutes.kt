package com.basket.server.routes

import com.basket.server.models.*
import com.basket.server.plugins.ErrorResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*
import java.util.concurrent.ConcurrentHashMap

// In-memory storage for baskets (in a real app, this would be a database)
private val baskets = ConcurrentHashMap<String, Basket>()

fun Route.basketRoutes() {
    route("/baskets") {
        // Create a new basket
        post {
            val request = call.receive<CreateBasketRequest>()

            // Generate a unique ID for the basket
            val basketId = UUID.randomUUID().toString()

            // Create the basket
            val basket = Basket(
                id = basketId,
                customerId = request.customerId,
                sharedBasket = request.sharedBasket
            )

            // Store the basket
            baskets[basketId] = basket

            // Return the created basket
            call.respond(
                HttpStatusCode.Created,
                CreateBasketResponse(basket = basket)
            )
        }

        // Get a basket by ID
        get("/{id}") {
            val basketId =
                call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Missing basket ID")

            // Find the basket
            val basket = baskets[basketId] ?: return@get call.respond(
                HttpStatusCode.NotFound,
                ErrorResponse(status = HttpStatusCode.NotFound.value, message = "Basket not found")
            )

            // Return the basket
            call.respond(HttpStatusCode.OK, basket)
        }

        // Add an item to a basket
        post("/{id}/items") {
            val basketId =
                call.parameters["id"] ?: return@post call.respond(HttpStatusCode.BadRequest, "Missing basket ID")
            val request = call.receive<AddItemRequest>()

            // Find the basket
            val basket = baskets[basketId] ?: return@post call.respond(HttpStatusCode.NotFound, "Basket not found")

            // Read products from the JSON file to get the price
            val productsFile = java.io.File("server/resources/product.json")
            val productsJson = productsFile.readText()
            val products = kotlinx.serialization.json.Json.decodeFromString<List<Product>>(productsJson)

            // Find the product
            val product = products.find { it.id == request.productId } ?: return@post call.respond(
                HttpStatusCode.NotFound,
                "Product not found"
            )

            // Create the basket item
            val basketItem = BasketItem(
                productId = request.productId,
                quantity = request.quantity,
                price = product.price
            )

            // Add the item to the basket
            val updatedItems = basket.items.toMutableList()

            // Check if the item already exists in the basket
            val existingItemIndex = updatedItems.indexOfFirst { it.productId == request.productId }
            if (existingItemIndex != -1) {
                // Update the existing item
                val existingItem = updatedItems[existingItemIndex]
                updatedItems[existingItemIndex] = existingItem.copy(quantity = existingItem.quantity + request.quantity)
            } else {
                // Add the new item
                updatedItems.add(basketItem)
            }

            // Update the basket
            val updatedBasket = basket.copy(items = updatedItems)
            baskets[basketId] = updatedBasket

            // Return the updated basket
            call.respond(HttpStatusCode.OK, updatedBasket)
        }
    }
}
