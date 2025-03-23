package com.basket.sample.scango.presentation.feature.home.viewmodel

import com.basket.sample.scango.presentation.core.viewmodel.BaseViewModel
import com.basket.sample.scango.presentation.feature.home.screen.state.HomeScreenActionState
import com.basket.sample.scango.presentation.feature.home.screen.state.HomeScreenEvent
import com.basket.sample.scango.presentation.feature.home.screen.state.HomeScreenState
import kotlinx.coroutines.flow.update

class HomeViewModel :
    BaseViewModel<HomeScreenState, HomeScreenEvent, HomeScreenActionState>(initialState = HomeScreenState()) {

    override fun sendScreenEvent(event: HomeScreenEvent) {
        TODO("Not yet implemented")
    }

    override fun clearErrorState() {
        if (_state.value.error != null) {
            _state.update { prevState ->
                prevState.copy(error = null)
            }
        }
    }
}
