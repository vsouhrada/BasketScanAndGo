package com.basket.sample.scango.domain.common.model

data class Coupon(
    val id: String,
    val title: String,
    val description: String,
    val discountPercentage: Int,
    val imageUrl: String,
    val expiryDate: String,
    val isUsed: Boolean = false
)
