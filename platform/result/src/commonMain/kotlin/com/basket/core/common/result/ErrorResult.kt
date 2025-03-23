package com.basket.core.common.result

open class ErrorResult<E>(
    open val error: E,
    override val message: String? = null,
    override val throwable: Throwable? = null
) : ErrorResultApi {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ErrorResult<*>) return false

        if (error != other.error) return false
        if (message != other.message) return false
        if (throwable != other.throwable) return false

        return true
    }

    override fun hashCode(): Int {
        var result = error?.hashCode() ?: 0
        result = 31 * result + (message?.hashCode() ?: 0)
        result = 31 * result + (throwable?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "ErrorResult(error=$error, message=$message, throwable=$throwable)"
    }
}

interface ErrorResultApi {

    val message: String?
    val throwable: Throwable?
}
