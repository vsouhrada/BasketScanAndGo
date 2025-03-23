package com.basket.sample.scango.presentation.feature.scango.basket.overview.screen.state

import com.basket.core.common.designSystem.uikit.screen.UIState
import com.basket.sample.scango.presentation.feature.scango.basket.overview.screen.model.BasketVo

data class BasketScreenState(
    override val data: BasketVo? = null,
    override val isLoading: Boolean = false,
    override val error: BasketScreenErrorState? = null,
) : UIState<BasketVo, BasketScreenErrorState>()

sealed class BasketScreenErrorState {
    data class UnexpectedError(
        val title: String,
        val message: String,
    ) : BasketScreenErrorState()
}
