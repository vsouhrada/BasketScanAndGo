package com.basket.sample.scango.data.feature.basket.repository.datasource.api.model

import com.basket.sample.scango.domain.feature.user.common.model.UserId
import kotlinx.serialization.Serializable

@Serializable
data class BasketDto(
    val id: String,
    val customerId: UserId,
    val sharedBasket: Boolean,
)
