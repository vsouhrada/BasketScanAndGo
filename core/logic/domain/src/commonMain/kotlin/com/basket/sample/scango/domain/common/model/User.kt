package com.basket.sample.scango.domain.common.model

typealias UserId = String

data class User(
    val id: String,
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val profilePictureUrl: String? = null,
)
