package com.basket.server.models

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val profilePictureUrl: String,
    // In a real application, this should be hashed
    val password: String,
    val salutation: String,
    val languageId: String,
)

@Serializable
data class UserResponse(
    val id: String,
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val profilePictureUrl: String,
    val salutation: String,
    val languageId: String,
    // Password is not included in the response
)

@Serializable
data class LoginRequest(
    val username: String,
    val password: String
)

@Serializable
data class TokenResponse(
    val accessToken: String,
    val refreshToken: String
)
