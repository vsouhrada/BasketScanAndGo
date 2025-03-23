package com.basket.sample.scango.data.feature.user.common.repository

import com.basket.core.common.result.Result
import com.basket.core.common.result.failure.FailureResult
import com.basket.core.common.result.failure.createFailureResult
import com.basket.core.common.result.toSuccess
import com.basket.sample.scango.domain.error.GetActiveUserError
import com.basket.sample.scango.domain.error.GetUserError
import com.basket.sample.scango.domain.error.UnexpectedError
import com.basket.sample.scango.domain.feature.user.common.model.User
import com.basket.sample.scango.domain.feature.user.common.model.UserId
import com.basket.sample.scango.domain.feature.user.common.repository.UserRepository

class UserRepositoryImpl : UserRepository {

    private lateinit var currentUser: User // TODO just for test

    override fun saveActiveUser(currentUser: User) {
        this.currentUser = currentUser
    }

    override fun getLoggedUser(): Result<User, FailureResult<GetActiveUserError>> {
        return this.currentUser.toSuccess()
    }

    override fun getUserById(userId: UserId): Result<User, FailureResult<GetUserError>> {
        val users = listOf(
            User.Customer(
                userId = userId,
                userName = "007",
                firstName = "John",
                lastName = "Snow",
                salutation = "",
                languageId = "",
                additionalProperties = emptyMap(),
                paymentInAppAllowed = true
            )
        )

        return users.find { it.userId == userId }?.toSuccess()
            ?: createFailureResult(UnexpectedError("User not found!"))
    }
}
