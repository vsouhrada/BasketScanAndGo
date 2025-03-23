package com.basket.sample.scango.data.feature.authorization.token.repository.datasource.api.v1.dto

import com.basket.core.common.ktime.OffsetDateTime
import com.basket.sample.scango.domain.common.model.UserId
import kotlinx.serialization.Serializable

@Serializable
data class CreateBasketRequestDto(
    val customerId: UserId,
    val sharedBasket: Boolean,
    val createdTimestampUTC: OffsetDateTime,
)

@Serializable
data class CreateBasketResponseDto(
    val basket: BasketDto,
)
