package com.basket.server.plugins

import com.basket.server.models.ErrorResponse
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond

fun Application.configureExceptionHandling() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            val status =
                when (cause) {
                    is IllegalArgumentException -> HttpStatusCode.BadRequest
                    is NoSuchElementException -> HttpStatusCode.NotFound
                    is SecurityException -> HttpStatusCode.Unauthorized
                    else -> HttpStatusCode.InternalServerError
                }

            call.respond(
                status,
                ErrorResponse(
                    status = status.value,
                    message = cause.message ?: "An error occurred",
                ),
            )
        }
    }
}
