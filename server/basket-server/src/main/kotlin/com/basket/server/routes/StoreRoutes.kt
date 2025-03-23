package com.basket.server.routes

import com.basket.server.models.Store
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import kotlinx.serialization.json.Json
import java.io.File

fun Route.storeRoutes() {
    route("/stores") {
        // Get all stores
        get {
            // Read stores from the JSON file
            val storesFile = File("server/resources/stores.json")
            val storesJson = storesFile.readText()
            val stores = Json.decodeFromString<List<Store>>(storesJson)

            // Return the stores
            call.respond(HttpStatusCode.OK, stores)
        }

        // Get a store by ID
        get("/{id}") {
            val storeId = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Missing store ID")

            // Read stores from the JSON file
            val storesFile = File("server/resources/stores.json")
            val storesJson = storesFile.readText()
            val stores = Json.decodeFromString<List<Store>>(storesJson)

            // Find the store
            val store = stores.find { it.id == storeId } ?: return@get call.respond(HttpStatusCode.NotFound, "Store not found")

            // Return the store
            call.respond(HttpStatusCode.OK, store)
        }
    }
}
