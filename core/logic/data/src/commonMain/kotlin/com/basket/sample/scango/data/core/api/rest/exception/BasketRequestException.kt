package com.basket.sample.scango.data.core.api.rest.exception

import com.basket.core.common.result.ErrorResult
import com.basket.sample.scango.data.core.api.rest.model.BasketRequestErrorData
import com.basket.sample.scango.data.core.api.rest.model.ErrorDto
import com.basket.sample.scango.data.core.api.rest.model.ProblemDto

class BasketRequestException(
    val errorCode: Int,
    val errorData: BasketRequestErrorData,
) : Exception()

fun BasketRequestException.toError() =
    ErrorResult(
        error =
            ErrorDto(
                problem =
                    ProblemDto(
                        code = errorData.code,
                        message = errorData.message ?: message ?: "",
                    ),
            ),
        throwable = this,
    )
