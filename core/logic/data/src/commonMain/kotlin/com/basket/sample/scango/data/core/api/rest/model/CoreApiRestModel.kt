package com.basket.sample.scango.data.core.api.rest.model

import kotlinx.serialization.Serializable

data class ErrorDto(
    val problem: ProblemDto?,
)

data class ProblemDto(
    val code: String?,
    val message: String,
) {
    fun hasCode() = code?.isNotBlank() ?: super.hashCode()
}

@Serializable
data class ErrorModel(
    val key: String,
    val value: String,
)

@Serializable
data class BasketRequestErrorData(
    val code: String?,
    val message: String?,
)
