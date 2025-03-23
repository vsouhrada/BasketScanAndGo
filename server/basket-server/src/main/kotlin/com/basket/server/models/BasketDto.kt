package com.basket.server.models

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class BasketDto(
    val id: String,
    val customerId: String,
    val sharedBasket: Boolean,
    val createdAt: Instant = Clock.System.now(),
    val items: List<BasketItem> = emptyList(),
)

@Serializable
data class BasketItem(
    val productId: String,
    val quantity: Int,
    val price: Double,
)

@Serializable
data class CreateBasketRequest(
    val customerId: String,
    val sharedBasket: Boolean,
)

@Serializable
data class CreateBasketResponse(
    val basket: BasketDto,
)

@Serializable
data class AddItemRequest(
    val productId: String,
    val quantity: Int,
)
