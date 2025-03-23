package com.basket.sample.scango.presentation.feature.scango.basket.overview.screen.state

sealed class BasketScreenEvent {

    data class SetActiveBasket(val basketId: String) : BasketScreenEvent()

}
