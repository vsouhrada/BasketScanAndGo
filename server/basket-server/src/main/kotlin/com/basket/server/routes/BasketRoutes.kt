package com.basket.server.routes

import com.basket.server.models.AddItemRequest
import com.basket.server.models.BasketDto
import com.basket.server.models.BasketItem
import com.basket.server.models.CreateBasketRequest
import com.basket.server.models.CreateBasketResponse
import com.basket.server.models.Product
import com.basket.server.plugins.ErrorResponse
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

// In-memory storage for baskets (in a real app, this would be a database)
private val baskets = ConcurrentHashMap<String, BasketDto>()

fun Route.basketRoutes() {
    route("/baskets") {
        // Create a new basket
        post {
            val request = call.receive<CreateBasketRequest>()

            // Generate a unique ID for the basket
            val basketId = UUID.randomUUID().toString()

            // Create the basket
            val basket =
                BasketDto(
                    id = basketId,
                    customerId = request.customerId,
                    sharedBasket = request.sharedBasket,
                )

            // Store the basket
            baskets[basketId] = basket

            // Return the created basket
            call.respond(
                HttpStatusCode.Created,
                CreateBasketResponse(basket = basket),
            )
        }

        // Get all baskets
        get {
            val baskets = baskets.values.map { basket ->
                BasketDto(
                    id = basket.id,
                    customerId = basket.customerId,
                    sharedBasket = basket.sharedBasket,
                    items = basket.items,
                    createdAt = basket.createdAt
                )
            }

            // Return the basket
            call.respond(HttpStatusCode.OK, baskets)
        }

        // Get a basket by ID
        get("/{id}") {
            val basketId =
                call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Missing basket ID")

            // Find the basket
            val basket =
                baskets[basketId] ?: return@get call.respond(
                    HttpStatusCode.NotFound,
                    ErrorResponse(status = HttpStatusCode.NotFound.value, message = "Basket not found"),
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
            val product =
                products.find { it.id == request.productId } ?: return@post call.respond(
                    HttpStatusCode.NotFound,
                    "Product not found",
                )

            // Create the basket item
            val basketItem =
                BasketItem(
                    productId = request.productId,
                    quantity = request.quantity,
                    price = product.price,
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
