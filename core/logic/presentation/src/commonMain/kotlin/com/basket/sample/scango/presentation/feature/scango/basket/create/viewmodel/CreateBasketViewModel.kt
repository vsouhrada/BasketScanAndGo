package com.basket.sample.scango.presentation.feature.scango.basket.create.viewmodel

import basketscanandgo.presentation.generated.resources.Res
import basketscanandgo.presentation.generated.resources.error_something_wrong
import com.basket.core.common.ktime.OffsetDateTime
import com.basket.core.common.result.chain
import com.basket.sample.scango.domain.common.model.User
import com.basket.sample.scango.domain.feature.basket.active.SetActiveBasketRequest
import com.basket.sample.scango.domain.feature.basket.active.SetActiveBasketUseCase
import com.basket.sample.scango.domain.feature.basket.create.usecase.CreateBasketRequest
import com.basket.sample.scango.domain.feature.basket.create.usecase.CreateBasketUseCase
import com.basket.sample.scango.domain.feature.user.getActiveUser.usecase.GetActiveUserUseCase
import com.basket.sample.scango.presentation.core.viewmodel.BaseViewModel
import com.basket.sample.scango.presentation.feature.scango.basket.create.screen.state.CreateBasketScreenActionState
import com.basket.sample.scango.presentation.feature.scango.basket.create.screen.state.CreateBasketScreenErrorState
import com.basket.sample.scango.presentation.feature.scango.basket.create.screen.state.CreateBasketScreenEvent
import com.basket.sample.scango.presentation.feature.scango.basket.create.screen.state.CreateBasketScreenState

class CreateBasketViewModel(
    private val createBasket: CreateBasketUseCase,
    private val getActiveUser: GetActiveUserUseCase,
    private val setActiveBasket: SetActiveBasketUseCase,
) : BaseViewModel<CreateBasketScreenState, CreateBasketScreenEvent, CreateBasketScreenActionState>(
    initialState = CreateBasketScreenState(),
) {
    override fun sendScreenEvent(event: CreateBasketScreenEvent) {
        when (event) {
            CreateBasketScreenEvent.CreateBasketEvent -> {
                createNewBasket()
            }
        }
    }

    override fun clearErrorState() {
        if (_state.value.error != null) {
            updateState { prevState ->
                prevState.copy(error = null)
            }
        }
    }

    private fun createNewBasket() {
        launch {
            getActiveUser().onFailure { error ->

                updateState { prevState ->
                    prevState.copy(
                        error =
                        CreateBasketScreenErrorState.UnexpectedError(
                            title = Res.string.error_something_wrong,
                            message = "Failed to get active user: ${error.error}",
                        ),
                    )
                }
            }.chain { userResponse ->
                createBasket(
                    params = buildCreateBasketRequest(user = userResponse.currentUser),
                ).onFailure { error ->
                    updateState { prevState ->
                        prevState.copy(
                            error =
                            CreateBasketScreenErrorState.UnexpectedError(
                                title = Res.string.error_something_wrong,
                                message = "Failed to create basket: ${error.error}",
                            ),
                        )
                    }
                }.chain { createBasketResponse ->
                    setActiveBasket(
                        params =
                        SetActiveBasketRequest(
                            basket = createBasketResponse.basket,
                        ),
                    ).onSuccess { activeBasket ->
                        emitScreenAction(CreateBasketScreenActionState.BasketCreated(basketId = activeBasket.basket.id))
                    }.onFailure { error ->
                        updateState { prevState ->
                            prevState.copy(
                                error =
                                CreateBasketScreenErrorState.UnexpectedError(
                                    title = Res.string.error_something_wrong,
                                    message = "Failed to set active basket: ${error.error}",
                                ),
                            )
                        }
                    }
                }
            }
        }
    }

    private fun buildCreateBasketRequest(user: User) = CreateBasketRequest(
        customerId = user.id,
        sharedBasket = false,
        createdTimestampUTC = OffsetDateTime.now(),
    )
}
