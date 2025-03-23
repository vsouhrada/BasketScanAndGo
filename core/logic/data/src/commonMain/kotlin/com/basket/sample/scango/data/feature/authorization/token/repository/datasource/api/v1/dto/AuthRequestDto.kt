package com.basket.sample.scango.data.feature.authorization.token.repository.datasource.api.v1.dto

import kotlinx.serialization.Serializable

@Serializable
data class AuthRequestDto(
    val username: String,
    val password: String
)
