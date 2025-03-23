package com.basket.sample.scango.presentation.feature.scango.basket.create.screen.state

import com.basket.core.common.designSystem.uikit.screen.UIState
import com.basket.sample.scango.presentation.feature.scango.basket.create.screen.model.CreateBasketVo
import org.jetbrains.compose.resources.StringResource

data class CreateBasketScreenState(
    override val data: CreateBasketVo? = null,
    override val isLoading: Boolean = false,
    override val error: CreateBasketScreenErrorState? = null,
) : UIState<CreateBasketVo, CreateBasketScreenErrorState>()

sealed class CreateBasketScreenErrorState {

    data class UnexpectedError(
        val title: StringResource,
        val message: String,
        val arguments: List<String> = emptyList(),
    ) : CreateBasketScreenErrorState()
}
