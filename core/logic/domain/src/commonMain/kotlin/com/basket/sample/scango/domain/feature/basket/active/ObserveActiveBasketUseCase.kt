package com.basket.sample.scango.domain.feature.basket.active

import com.basket.sample.scango.domain.common.model.Basket
import com.basket.sample.scango.domain.core.usecase.ObservableUseCaseNoParams
import com.basket.sample.scango.domain.error.ObserveActiveBasketError

data class ObserveActiveBasketResponse(
    val basket: Basket,
)

abstract class ObserveActiveBasketUseCase :
    ObservableUseCaseNoParams<ObserveActiveBasketResponse, ObserveActiveBasketError>()
