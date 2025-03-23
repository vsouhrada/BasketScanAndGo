package com.basket.server.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val status: Int,
    val message: String
)

fun Application.configureExceptionHandling() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            val status = when (cause) {
                is IllegalArgumentException -> HttpStatusCode.BadRequest
                is NoSuchElementException -> HttpStatusCode.NotFound
                is SecurityException -> HttpStatusCode.Unauthorized
                else -> HttpStatusCode.InternalServerError
            }
            
            call.respond(
                status,
                ErrorResponse(
                    status = status.value,
                    message = cause.message ?: "An error occurred"
                )
            )
        }
    }
}
