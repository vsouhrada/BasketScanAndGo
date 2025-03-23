package com.basket.sample.scango.data.core.api.rest.exception

import com.basket.core.common.result.ErrorResult
import com.basket.sample.scango.data.core.api.rest.model.ErrorDto
import com.basket.sample.scango.data.core.api.rest.model.ProblemDto
import io.ktor.http.HttpStatusCode

class BasketRequestTimeoutException(
    val throwable: Throwable,
) : Exception()

fun BasketRequestTimeoutException.toError() =
    ErrorResult(
        error =
            ErrorDto(
                problem =
                    ProblemDto(
                        code = HttpStatusCode.RequestTimeout.value.toString(),
                        message = "Server not responding, try again later.",
                    ),
            ),
        throwable = this,
    )
