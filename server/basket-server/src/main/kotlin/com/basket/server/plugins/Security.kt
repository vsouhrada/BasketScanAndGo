package com.basket.server.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt

fun Application.configureSecurity() {
    // JWT configuration
    val jwtSecret = environment.config.propertyOrNull("jwt.secret")?.getString() ?: "default-secret-key"
    val jwtIssuer = environment.config.propertyOrNull("jwt.issuer")?.getString() ?: "basket-server"
    val jwtAudience = environment.config.propertyOrNull("jwt.audience")?.getString() ?: "basket-client"
    val jwtRealm = environment.config.propertyOrNull("jwt.realm")?.getString() ?: "basket-app"

    install(Authentication) {
        jwt("auth-jwt") {
            realm = jwtRealm
            verifier(
                JWT
                    .require(Algorithm.HMAC256(jwtSecret))
                    .withAudience(jwtAudience)
                    .withIssuer(jwtIssuer)
                    .build(),
            )
            validate { credential ->
                if (credential.payload.audience.contains(jwtAudience)) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
        }
    }
}

// Helper function to generate JWT token
fun generateJwtToken(userId: String, jwtSecret: String, jwtIssuer: String, jwtAudience: String,): String {
    return JWT.create()
        .withAudience(jwtAudience)
        .withIssuer(jwtIssuer)
        .withClaim("userId", userId)
        .sign(Algorithm.HMAC256(jwtSecret))
}
