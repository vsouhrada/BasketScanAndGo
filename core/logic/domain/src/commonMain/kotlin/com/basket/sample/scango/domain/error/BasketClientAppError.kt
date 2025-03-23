package com.basket.sample.scango.domain.error

import com.basket.core.common.result.failure.Error
import com.basket.sample.scango.domain.common.model.Problem

interface BasketBaseError : Error {
    val problem: Problem?
    val message: String

    fun getProblemOrDefaultMessage() = /*problem?.getErrorMessage() ?:*/ message

    fun getProblemMessageOrDefault(defaultMsg: () -> String): String {
        return /*problem?.getErrorMessage() ?:*/ defaultMsg()
    }
}

sealed interface BasketClientCommonError :
    Error,
    GetActiveUserError,
    GetUserError,
    SaveActiveUserError,
    FetchTokenInfoError,
    CreateBasketError,
    ObserveActiveBasketError,
    SetActiveBasketError,
    GetActiveBasketError

data class UnexpectedError(
    override val problem: Problem? = null,
    override val message: String = "Unexpected Error in the Process",
) : BasketClientCommonError

data class MissingRequiredDataError(
    override val message: String = "Missing a required data",
    override val problem: Problem? = null,
) : BasketClientCommonError

data object ActiveBasketNotFound :
    GetActiveBasketError,
    ObserveActiveBasketError

fun createMissingRequiredDataError(problem: Problem? = null) = MissingRequiredDataError(
    message = "Missing a Required Data",
    problem = problem,
)
