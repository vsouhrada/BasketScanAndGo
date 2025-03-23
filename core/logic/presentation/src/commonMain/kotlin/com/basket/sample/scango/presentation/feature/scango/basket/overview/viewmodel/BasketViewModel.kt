package com.basket.sample.scango.presentation.feature.scango.basket.overview.viewmodel

import com.basket.sample.scango.presentation.core.viewmodel.BaseViewModel
import com.basket.sample.scango.presentation.feature.scango.basket.overview.screen.state.BasketScreenActionState
import com.basket.sample.scango.presentation.feature.scango.basket.overview.screen.state.BasketScreenEvent
import com.basket.sample.scango.presentation.feature.scango.basket.overview.screen.state.BasketScreenState
import kotlinx.coroutines.flow.update

class BasketViewModel : BaseViewModel<BasketScreenState, BasketScreenEvent, BasketScreenActionState>(
    initialState = BasketScreenState(),
) {
    override fun sendScreenEvent(event: BasketScreenEvent) {
        when (event) {
            is BasketScreenEvent.SetActiveBasket -> {
                // TODO()
            }
        }
    }

    override fun clearErrorState() {
        if (_state.value.error != null) {
            _state.update { prevState ->
                prevState.copy(error = null)
            }
        }
    }
}
