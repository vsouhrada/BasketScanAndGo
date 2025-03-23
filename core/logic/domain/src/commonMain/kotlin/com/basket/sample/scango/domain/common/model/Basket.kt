package com.basket.sample.scango.domain.common.model

import com.basket.sample.scango.domain.feature.user.common.model.UserId

typealias Id = Long
typealias BasketId = String

data class Basket(
    val id: BasketId,
    val customerId: UserId,
    val sharedBasket: Boolean,
    val items: List<BasketItem> = emptyList(),
    val totalItems: Int = items.sumOf { it.quantity },
    val totalPrice: Double = items.sumOf { it.totalPrice }
)

data class BasketItem(
    val id: String,
    val product: Product,
    val quantity: Int,
    val totalPrice: Double = product.price * quantity,
)

