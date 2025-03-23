package com.basket.sample.scango.data.feature.authorization.token.repository.datasource.api.v1.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    @SerialName("id")
    val userId: String,
    val username: String,
    val firstName: String,
    val lastName: String,
    val salutation: String,
    val languageId: String,
    val profilePictureUrl: String?,
    val email: String,
)
