package com.basket.sample.scango.data.feature.authorization.token.repository.datasource.api.v1.dto

import com.basket.sample.scango.domain.common.model.UserId
import kotlinx.serialization.Serializable

@Serializable
data class BasketDto(
    val id: String,
    val customerId: UserId,
    val sharedBasket: Boolean,
)
