package com.basket.sample.scango.domain.feature.user.common.model

typealias UserId = String

sealed class User {
    abstract val userId: UserId
    abstract val userName: String
    abstract val firstName: String
    abstract val lastName: String
    abstract val salutation: String
    abstract val languageId: String
    abstract val additionalProperties: Map<String, String>

    fun isEmployee() = when (this) {
        is Cashier,
        is MainCashier -> true

        else -> false
    }

    data class Customer(
        override val userId: UserId,
        override val userName: String,
        override val firstName: String,
        override val lastName: String,
        override val salutation: String,
        override val languageId: String,
        override val additionalProperties: Map<String, String>,
        val customerGroupIdList: List<String> = emptyList(),
        val paymentInAppAllowed: Boolean,
    ) : User()

    data class Cashier(
        override val userId: UserId,
        override val userName: String,
        override val firstName: String,
        override val lastName: String,
        override val salutation: String,
        override val languageId: String,
        override val additionalProperties: Map<String, String>
    ) : User()

    data class MainCashier(
        override val userId: UserId,
        override val userName: String,
        override val firstName: String,
        override val lastName: String,
        override val salutation: String,
        override val languageId: String,
        override val additionalProperties: Map<String, String>
    ) : User()
}
