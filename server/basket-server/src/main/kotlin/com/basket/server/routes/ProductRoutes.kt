package com.basket.server.routes

import com.basket.server.models.Product
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import kotlinx.serialization.json.Json
import java.io.File

fun Route.productRoutes() {
    route("/products") {
        // Get all products
        get {
            // Read products from the JSON file
            val productsFile = File("server/resources/product.json")
            val productsJson = productsFile.readText()
            val products = Json.decodeFromString<List<Product>>(productsJson)

            // Return the products
            call.respond(HttpStatusCode.OK, products)
        }

        // Get a product by ID
        get("/{id}") {
            val productId =
                call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Missing product ID")

            // Read products from the JSON file
            val productsFile = File("server/resources/product.json")
            val productsJson = productsFile.readText()
            val products = Json.decodeFromString<List<Product>>(productsJson)

            // Find the product
            val product =
                products.find { it.id == productId } ?: return@get call.respond(
                    HttpStatusCode.NotFound,
                    "Product not found",
                )

            // Return the product
            call.respond(HttpStatusCode.OK, product)
        }
    }
}
