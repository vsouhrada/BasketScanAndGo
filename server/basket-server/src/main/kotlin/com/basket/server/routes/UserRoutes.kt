package com.basket.server.routes

import com.basket.server.models.User
import com.basket.server.models.UserResponse
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import kotlinx.serialization.json.Json
import java.io.File

fun Route.userRoutes() {
    route("/users") {
        // Get all users
        get {
            // Read users from the JSON file
            val usersFile = File("server/resources/users.json")
            val usersJson = usersFile.readText()
            val users = Json.decodeFromString<List<User>>(usersJson)

            // Convert to UserResponse to exclude passwords
            val userResponses =
                users.map { user ->
                    UserResponse(
                        id = user.id,
                        username = user.username,
                        email = user.email,
                        firstName = user.firstName,
                        lastName = user.lastName,
                        profilePictureUrl = user.profilePictureUrl,
                        salutation = user.salutation,
                        languageId = user.languageId,
                    )
                }

            // Return the users
            call.respond(HttpStatusCode.OK, userResponses)
        }

        // Get a user by ID
        get("/{id}") {
            val userId = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Missing user ID")

            // Read users from the JSON file
            val usersFile = File("server/resources/users.json")
            val usersJson = usersFile.readText()
            val users = Json.decodeFromString<List<User>>(usersJson)

            // Find the user
            val user = users.find { it.id == userId } ?: return@get call.respond(HttpStatusCode.NotFound, "User not found")

            // Convert to UserResponse to exclude password
            val userResponse =
                UserResponse(
                    id = user.id,
                    username = user.username,
                    email = user.email,
                    firstName = user.firstName,
                    lastName = user.lastName,
                    profilePictureUrl = user.profilePictureUrl,
                    salutation = user.salutation,
                    languageId = user.languageId,
                )

            // Return the user
            call.respond(HttpStatusCode.OK, userResponse)
        }
    }
}
