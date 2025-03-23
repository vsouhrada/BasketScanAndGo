package com.basket.sample.scango.domain.error

import com.basket.core.common.result.failure.Error

sealed interface BasketClientCommonError : Error,
    GetActiveUserError,
    GetUserError,
    SaveActiveUserError,
    FetchTokenInfoError,
    CreateBasketError,
    ObserveActiveBasketError,
    SetActiveBasketError,
    GetActiveBasketError

data class UnexpectedError(
    val message: String,
) : BasketClientCommonError

data object ActiveBasketNotFound :
    GetActiveBasketError,
    ObserveActiveBasketError
