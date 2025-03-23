package com.basket.sample.scango.presentation.feature.home.screen.state

import com.basket.core.common.designSystem.uikit.screen.UIState
import com.basket.sample.scango.presentation.feature.home.screen.model.HomeVo

data class HomeScreenState(
    override val data: HomeVo? = null,
    override val isLoading: Boolean = false,
    override val error: HomeScreenErrorState? = null,
) : UIState<HomeVo, HomeScreenErrorState>()

sealed class HomeScreenErrorState {
    data class UnexpectedError(
        val title: String,
        val message: String,
    ) : HomeScreenErrorState()
}
