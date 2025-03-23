package com.basket.sample.scango.domain.feature.basket.active

import com.basket.sample.scango.domain.common.model.Basket
import com.basket.sample.scango.domain.core.usecase.UseCaseNoParams
import com.basket.sample.scango.domain.error.GetActiveBasketError

data class GetActiveBasketResponse(
    val basket: Basket
)

abstract class GetActiveBasketUseCase : UseCaseNoParams<GetActiveBasketResponse, GetActiveBasketError>()
