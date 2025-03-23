package com.basket.sample.scango.data.feature.authorization.token.repository.datasource.api.v1.dto

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class BasketDto(
    val id: String,
    val customerId: String,
    val sharedBasket: Boolean,
    val createdAt: Instant = Clock.System.now(),
    val items: List<BasketItemDto> = emptyList(),
)

@Serializable
data class BasketItemDto(
    val id: String,
    val product: ProductDto,
    val quantity: Int,
    val totalPrice: Double,
)

@Serializable
data class ProductDto(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val category: String,
    val isSpecialOffer: Boolean = false,
    val discountPercentage: Int = 0,
)
