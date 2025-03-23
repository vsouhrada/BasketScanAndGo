package com.basket.sample.scango.presentation.feature.login.viewmodel

import androidx.lifecycle.viewModelScope
import com.basket.core.common.klogger.getKLogger
import com.basket.core.common.klogger.logDebug
import com.basket.core.common.result.chain
import com.basket.sample.scango.domain.feature.user.getUser.usecase.GetUserRequest
import com.basket.sample.scango.domain.feature.user.getUser.usecase.GetUserUseCase
import com.basket.sample.scango.domain.feature.user.saveActiveUser.usecase.SaveActiveUserRequest
import com.basket.sample.scango.domain.feature.user.saveActiveUser.usecase.SaveActiveUserUseCase
import com.basket.sample.scango.presentation.core.viewmodel.BaseViewModel
import com.basket.sample.scango.presentation.feature.login.model.UserCredentials
import com.basket.sample.scango.presentation.feature.login.screen.state.LoginScreenActionState
import com.basket.sample.scango.presentation.feature.login.screen.state.LoginScreenErrorState
import com.basket.sample.scango.presentation.feature.login.screen.state.LoginScreenEvent
import com.basket.sample.scango.presentation.feature.login.screen.state.LoginScreenState
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

class LoginViewModel(
    private val getUser: GetUserUseCase,
    private val saveActiveUser: SaveActiveUserUseCase,
) : BaseViewModel<LoginScreenState, LoginScreenEvent, LoginScreenActionState>(initialState = LoginScreenState()) {
    private val logger = getKLogger { }

    override fun clearErrorState() {
        if (_state.value.error != null) {
            _state.update { prevState ->
                prevState.copy(error = null)
            }
        }
    }

    @Throws(CancellationException::class)
    override fun sendScreenEvent(event: LoginScreenEvent) {
        when (event) {
            is LoginScreenEvent.AuthorizeUser ->
                viewModelScope.launch {
                    authorizeUser(event.userCredentials)
                }

            LoginScreenEvent.RegisterNewUser -> TODO()
        }
    }

    private fun authorizeUser(userCredentials: UserCredentials) {
        logger.logDebug { "Authorization of user=${userCredentials.userId}" }
        viewModelScope.launch {
            emitLoadingState(isLoading = true)

            getUser(
                params =
                GetUserRequest(
                    userId = userCredentials.userId,
                ),
            ).chain { getUserResponse ->
                saveActiveUser(
                    params =
                    SaveActiveUserRequest(
                        currentUser = getUserResponse.user,
                    ),
                ).onSuccess { saveActiveUserResponse ->
                    logger.logDebug { "Successfully saved activeUser=$saveActiveUser" }
                    emitScreenAction(LoginScreenActionState.UserAuthorized)
                }
            }.onFailure {
                logger.error { "Failed to send authorization to user=${userCredentials.userId}, failure: $it" }
                emitErrorState(
                    errorState = LoginScreenErrorState.InvalidUserCredentials(userId = userCredentials.userId),
                )
            }
        }
    }

    private fun emitScreenState(newState: LoginScreenState) {
        _state.update {
            newState
        }
    }

    private suspend fun emitAction(action: LoginScreenActionState) {
        _actionState.emit(action)
    }

    private fun emitErrorState(errorState: LoginScreenErrorState) {
        _state.update { prevState ->
            prevState.copy(error = errorState, isLoading = false)
        }
    }

    private fun emitLoadingState(isLoading: Boolean) {
        _state.update { prevState ->
            prevState.copy(isLoading = isLoading)
        }
    }
}
