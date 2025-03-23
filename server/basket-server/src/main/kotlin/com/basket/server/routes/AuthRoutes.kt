package com.basket.server.routes

import com.basket.server.models.LoginRequest
import com.basket.server.models.TokenResponse
import com.basket.server.models.User
import com.basket.server.plugins.generateJwtToken
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.application
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import kotlinx.serialization.json.Json
import java.io.File
import java.util.UUID

fun Route.authRoutes() {
    route("/auth") {
        post("/login") {
            val loginRequest = call.receive<LoginRequest>()

            // Read users from the JSON file
            val usersFile = File("server/resources/users.json")
            val usersJson = usersFile.readText()
            val users = Json.decodeFromString<List<User>>(usersJson)

            // Find the user with the matching username and password
            val user = users.find { it.username == loginRequest.username && it.password == loginRequest.password }

            if (user != null) {
                // Generate JWT token
                val jwtSecret = application.environment.config.propertyOrNull("jwt.secret")?.getString() ?: "default-secret-key"
                val jwtIssuer = application.environment.config.propertyOrNull("jwt.issuer")?.getString() ?: "basket-server"
                val jwtAudience = application.environment.config.propertyOrNull("jwt.audience")?.getString() ?: "basket-client"

                val accessToken = generateJwtToken(user.id, jwtSecret, jwtIssuer, jwtAudience)
                val refreshToken = UUID.randomUUID().toString() // In a real app, this would be stored and associated with the user

                call.respond(
                    HttpStatusCode.OK,
                    TokenResponse(
                        accessToken = accessToken,
                        refreshToken = refreshToken,
                    ),
                )
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Invalid username or password")
            }
        }

        post("/refresh") {
            // In a real app, we would validate the refresh token
            // For this example, we'll just generate a new token

            val jwtSecret = application.environment.config.propertyOrNull("jwt.secret")?.getString() ?: "default-secret-key"
            val jwtIssuer = application.environment.config.propertyOrNull("jwt.issuer")?.getString() ?: "basket-server"
            val jwtAudience = application.environment.config.propertyOrNull("jwt.audience")?.getString() ?: "basket-client"

            val userId = "user3" // In a real app, this would be extracted from the refresh token
            val accessToken = generateJwtToken(userId, jwtSecret, jwtIssuer, jwtAudience)
            val refreshToken = UUID.randomUUID().toString() // In a real app, this would be stored and associated with the user

            call.respond(
                HttpStatusCode.OK,
                TokenResponse(
                    accessToken = accessToken,
                    refreshToken = refreshToken,
                ),
            )
        }
    }
}
