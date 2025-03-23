package com.basket.sample.scango.data.feature.basket.repository.datasource.api.model

import com.basket.core.common.ktime.OffsetDateTime
import com.basket.sample.scango.domain.common.model.Basket
import com.basket.sample.scango.domain.feature.user.common.model.UserId
import kotlinx.serialization.Serializable

@Serializable
data class CreateBasketRequestDto(
    val customerId: UserId,
    val sharedBasket: Boolean,
    val createdTimestampUTC: OffsetDateTime,
)

data class CreateBasketResponseDto(
    val basket: BasketDto
)
