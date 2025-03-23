package com.basket.sample.scango.presentation.feature.scango.startTrip.screen.state

sealed class StartTripActionState {
    data class BeginShopping(val basketId: String?) : StartTripActionState()
}
