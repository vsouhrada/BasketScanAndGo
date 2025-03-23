package com.basket.sample.scango.data.common.mapper

import com.basket.core.common.result.ErrorResult
import com.basket.core.common.result.Result
import com.basket.core.common.result.failure.Error
import com.basket.core.common.result.failure.FailureResult
import com.basket.sample.scango.data.core.api.rest.model.ErrorDto
import com.basket.sample.scango.data.core.api.rest.model.ProblemDto
import com.basket.sample.scango.domain.common.model.Problem

fun <E : Error> Result.Failure<ErrorResult<ErrorDto>>.toFailureResult(
    error: E
): Result<Nothing, FailureResult<E>> {
    return Result.failure(
        FailureResult(
            error = error,
            diagnosticMessage = this.error.message,
            throwable = this.error.throwable
        )
    )
}

fun ProblemDto?.toDomain(): Problem? {
    return this?.let { dto ->
        Problem(
            code = dto.code,
            message = dto.message,
        )
    }
}
