package com.basket.sample.scango.domain.common.model

data class Product(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val category: String,
    val isSpecialOffer: Boolean = false,
    val discountPercentage: Int = 0
)
