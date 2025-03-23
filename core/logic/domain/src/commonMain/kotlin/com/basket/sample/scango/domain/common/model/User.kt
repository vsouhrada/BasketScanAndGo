package com.basket.sample.scango.domain.common.model

data class User(
    val id: String,
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val profilePictureUrl: String? = null
)
