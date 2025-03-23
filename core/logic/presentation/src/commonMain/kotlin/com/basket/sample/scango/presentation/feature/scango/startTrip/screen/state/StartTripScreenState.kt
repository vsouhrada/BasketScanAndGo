package com.basket.sample.scango.presentation.feature.scango.startTrip.screen.state

import com.basket.core.common.designSystem.uikit.screen.UIState
import com.basket.sample.scango.presentation.feature.scango.startTrip.screen.model.StartTripVo

data class StartTripScreenState(
    override val data: StartTripVo? = null,
    override val isLoading: Boolean = false,
    override val error: StartTripScreenErrorState? = null,
) : UIState<StartTripVo, StartTripScreenErrorState>() {
}

sealed class StartTripScreenErrorState {

    data class UnexpectedError(
        val title: String,
        val message: String
    ) : StartTripScreenErrorState()
}
