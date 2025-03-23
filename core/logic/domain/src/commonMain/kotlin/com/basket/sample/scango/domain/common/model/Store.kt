package com.basket.sample.scango.domain.common.model

data class Store(
    val id: String,
    val name: String,
    val address: String,
    val imageUrl: String,
    val openingHours: OpeningHours,
)

data class OpeningHours(
    val monday: String,
    val tuesday: String,
    val wednesday: String,
    val thursday: String,
    val friday: String,
    val saturday: String,
    val sunday: String,
)
