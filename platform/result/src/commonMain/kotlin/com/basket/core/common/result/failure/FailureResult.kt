package com.basket.core.common.result.failure

import com.basket.core.common.ktime.OffsetDateTime
import com.basket.core.common.result.Result

/**
 *
 * @since 1.0.0
 */
interface Error

/**
 *
 * @since 1.0.0
 */
open class FailureResult<out E : Error>(
    open val error: E,
    val timestamp: OffsetDateTime = OffsetDateTime.now(),
    val throwable: Throwable? = null,
    val diagnosticMessage: String? = null,
    val additionalData: MutableMap<String, Any> = mutableMapOf()
) {

    /**
     * @since 1.0.0
     */
    fun <F : Error> mapError(map: (error: E) -> F): FailureResult<F> {
        return FailureResult(
            error = map(error),
            timestamp = timestamp,
            throwable = throwable,
            diagnosticMessage = diagnosticMessage,
            additionalData = additionalData
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FailureResult<*>) return false

        if (error != other.error) return false
        if (timestamp != other.timestamp) return false
        if (throwable != other.throwable) return false
        if (diagnosticMessage != other.diagnosticMessage) return false
        if (additionalData != other.additionalData) return false

        return true
    }

    override fun hashCode(): Int {
        var result = error.hashCode()
        result = 31 * result + error.hashCode()
        result = 31 * result + timestamp.hashCode()
        result = 31 * result + (throwable?.hashCode() ?: 0)
        result = 31 * result + (diagnosticMessage?.hashCode() ?: 0)
        result = 31 * result + additionalData.hashCode()
        return result
    }

    override fun toString(): String {
        return "FailureResult(error=$error, timestamp=$timestamp, throwable=$throwable, diagnosticMessage=$diagnosticMessage, additionalData=$additionalData)"
    }
}

/**
 * Converts [Exception] to [Result.Failure] with a specific [error]
 * @since 1.0.0
 */
fun <E : Error> Exception.toFailure(
    error: E
): Result<Nothing, FailureResult<E>> {
    return Result.failure(
        FailureResult(
            error = error,
            throwable = this
        )
    )
}

/**
 * Converts [Throwable] to [Result.Failure] with a specific [error]
 * @since 1.0.0
 */
fun <E : Error> Throwable.toFailure(
    error: E
): Result<Nothing, FailureResult<E>> {
    return Result.failure(
        FailureResult(
            error = error,
            throwable = this
        )
    )
}

/**
 * It creates [Result.Failure] with a specific [error]
 * @since 1.0.0
 */
fun <E : Error> createFailureResult(
    error: E
): Result<Nothing, FailureResult<E>> {
    return Result.failure(
        FailureResult(error = error)
    )
}
