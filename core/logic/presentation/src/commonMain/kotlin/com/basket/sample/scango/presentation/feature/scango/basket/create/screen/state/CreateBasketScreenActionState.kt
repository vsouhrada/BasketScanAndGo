package com.basket.sample.scango.presentation.feature.scango.basket.create.screen.state

import com.basket.sample.scango.domain.common.model.BasketId

sealed class CreateBasketScreenActionState {
    data class BasketCreated(val basketId: BasketId) : CreateBasketScreenActionState()
}
