package com.basket.sample.scango.presentation.feature.login.screen.state

import com.basket.core.common.designSystem.uikit.screen.UIState
import com.basket.sample.scango.domain.feature.user.common.model.UserId
import com.basket.sample.scango.presentation.feature.login.model.LoginVo

data class LoginScreenState(
    override val data: LoginVo? = null,
    override val isLoading: Boolean = false,
    override val error: LoginScreenErrorState? = null,
) : UIState<LoginVo, LoginScreenErrorState>()

sealed class LoginScreenErrorState {

    data class UnexpectedError(
        val title: String,
        val message: String
    ) : LoginScreenErrorState()

    data class InvalidUserCredentials(
        val userId: UserId
    ) : LoginScreenErrorState()
}
