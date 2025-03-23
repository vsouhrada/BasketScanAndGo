package com.basket.sample.scango.domain.feature.basket.active

import com.basket.sample.scango.domain.common.model.Basket
import com.basket.sample.scango.domain.core.usecase.UseCase
import com.basket.sample.scango.domain.error.SetActiveBasketError

data class SetActiveBasketRequest(
    val basket: Basket
)

data class SetActiveBasketResponse(
    val basket: Basket
)

abstract class SetActiveBasketUseCase : UseCase<SetActiveBasketResponse, SetActiveBasketError, SetActiveBasketRequest>()
