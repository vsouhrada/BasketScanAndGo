package com.basket.sample.scango.domain.feature.authorization.model

data class TokenInfo(
    val accessToken: String,
    val refreshToken: String,
)