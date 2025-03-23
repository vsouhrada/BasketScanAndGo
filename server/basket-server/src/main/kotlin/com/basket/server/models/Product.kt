package com.basket.server.models

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val category: String,
    val isSpecialOffer: Boolean,
    val discountPercentage: Int,
)
