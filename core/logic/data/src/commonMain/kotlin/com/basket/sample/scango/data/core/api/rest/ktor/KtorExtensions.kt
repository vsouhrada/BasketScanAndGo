package com.basket.sample.scango.data.core.api.rest.ktor

import com.basket.core.common.result.ErrorResult
import com.basket.core.common.result.Result
import com.basket.core.common.result.toSuccess
import com.basket.sample.scango.data.core.api.rest.exception.BasketRequestException
import com.basket.sample.scango.data.core.api.rest.exception.BasketRequestTimeoutException
import com.basket.sample.scango.data.core.api.rest.exception.toError
import com.basket.sample.scango.data.core.api.rest.model.ErrorDto
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import kotlinx.io.IOException

suspend inline fun <reified DTO : Any> networkCall(
    tryCount: Int = 1,
    clientCall: () -> HttpResponse,
): Result<DTO, ErrorResult<ErrorDto>> {
    var tryLeft = tryCount
    while (tryLeft > 0) {
        try {
            return clientCall().body<DTO>().toSuccess()
        } catch (e: Throwable) {
            tryLeft--
            if (tryLeft <= 0 || e !is IOException) {
                return e.toFailureErrorResult()
            }
        }
    }
    throw IllegalStateException("Network try logic broken!!!")
}

fun Throwable.toFailureErrorResult(): Result<Nothing, ErrorResult<ErrorDto>> {
    return Result.failure(
        error =
            when (this) {
                is BasketRequestException -> this.toError()
                is BasketRequestTimeoutException -> this.toError()

                else -> {
                    ErrorResult(
                        error =
                            ErrorDto(
                                problem = null,
                            ),
                        throwable = this,
                        message = this.message,
                    )
                }
            },
    )
}

