package com.basket.sample.scango.domain.feature.basket.create.usecase

import com.basket.core.common.ktime.OffsetDateTime
import com.basket.sample.scango.domain.common.model.Basket
import com.basket.sample.scango.domain.core.usecase.UseCase
import com.basket.sample.scango.domain.error.CreateBasketError
import com.basket.sample.scango.domain.feature.user.common.model.UserId

data class CreateBasketResponse(
    val basket: Basket,
)

data class CreateBasketRequest(
    val customerId: UserId,
    val sharedBasket: Boolean,
    val createdTimestampUTC: OffsetDateTime,
)

abstract class CreateBasketUseCase() : UseCase<CreateBasketResponse, CreateBasketError, CreateBasketRequest>()
