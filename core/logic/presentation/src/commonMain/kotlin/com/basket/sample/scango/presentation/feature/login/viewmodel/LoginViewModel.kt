package com.basket.sample.scango.presentation.feature.login.viewmodel

import androidx.lifecycle.viewModelScope
import com.basket.core.common.klogger.getKLogger
import com.basket.core.common.klogger.logDebug
import com.basket.core.common.result.chain
import com.basket.core.common.result.failure.FailureResult
import com.basket.sample.scango.domain.feature.authorization.usecase.FetchTokenInfoRequest
import com.basket.sample.scango.domain.feature.authorization.usecase.FetchTokenInfoUseCase
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
    private val fetchTokenInfo: FetchTokenInfoUseCase,
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
        logger.logDebug { "Authorization of user=${userCredentials.username}" }
        viewModelScope.launch {
            emitLoadingState(isLoading = true)

            fetchTokenInfo(
                params =
                FetchTokenInfoRequest(
                    username = userCredentials.username,
                    password = userCredentials.password,
                ),
            ).chain { fetchTokenInfoResponse ->
                getUser(
                    params =
                    GetUserRequest(
                        userId = fetchTokenInfoResponse.tokenInfo.userId,
                    ),
                ).chain { getUserResponse ->
                    saveActiveUser(
                        params =
                        SaveActiveUserRequest(
                            currentUser = getUserResponse.user,
                        ),
                    ).onSuccess {
                        logger.logDebug { "Successfully saved active user=${getUserResponse.user.id}" }
                        emitScreenAction(LoginScreenActionState.UserAuthorized)
                    }
                }
            }.onFailure { failure ->
                logger.error { "Failed to authorize user=${userCredentials.username}, failure: $failure" }
                emitErrorState(
                    errorState = mapErrorState(
                        userCredentials = userCredentials,
                        failure = failure,
                    ),
                )
            }
        }
    }

    private fun mapErrorState(
        userCredentials: UserCredentials,
        failure: FailureResult<*>,
    ): LoginScreenErrorState {
        if (isConnectionFailure(failure = failure)) {
            return LoginScreenErrorState.UnexpectedError(
                title = "Server not reachable",
                message = "Unable to connect to backend server. Check Wi-Fi and server availability.",
            )
        }

        return LoginScreenErrorState.InvalidUserCredentials(username = userCredentials.username)
    }

    private fun isConnectionFailure(failure: FailureResult<*>): Boolean {
        val diagnostic = failure.diagnosticMessage.orEmpty()
        val throwableText = failure.throwable?.toString().orEmpty()
        val combinedMessage = "$diagnostic $throwableText".lowercase()

        return combinedMessage.contains("failed to connect") ||
            combinedMessage.contains("connectexception") ||
            combinedMessage.contains("timeout")
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
