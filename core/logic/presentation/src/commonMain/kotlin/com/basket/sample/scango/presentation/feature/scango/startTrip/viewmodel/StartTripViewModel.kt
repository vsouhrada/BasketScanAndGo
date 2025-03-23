package com.basket.sample.scango.presentation.feature.scango.startTrip.viewmodel

import androidx.lifecycle.viewModelScope
import com.basket.sample.scango.presentation.core.viewmodel.BaseViewModel
import com.basket.sample.scango.presentation.feature.scango.startTrip.screen.state.StartTripActionState
import com.basket.sample.scango.presentation.feature.scango.startTrip.screen.state.StartTripScreenEvent
import com.basket.sample.scango.presentation.feature.scango.startTrip.screen.state.StartTripScreenState
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StartTripViewModel :
    BaseViewModel<StartTripScreenState, StartTripScreenEvent, StartTripActionState>(
        initialState = StartTripScreenState(),
    ) {
    override fun sendScreenEvent(event: StartTripScreenEvent) {
        when (event) {
            StartTripScreenEvent.OnBeginShoppingEvent -> {
                viewModelScope.launch {
                    emitScreenAction(action = StartTripActionState.BeginShopping(null))
                }
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
