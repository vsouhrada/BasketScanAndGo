package com.basket.sample.scango.domain.feature.user.common.repository

import com.basket.core.common.result.Result
import com.basket.core.common.result.failure.FailureResult
import com.basket.sample.scango.domain.feature.user.common.model.User
import com.basket.sample.scango.domain.feature.user.common.model.UserId
import com.basket.sample.scango.domain.error.GetActiveUserError
import com.basket.sample.scango.domain.error.GetUserError

interface UserRepository {

    fun saveActiveUser(currentUser: User)

    fun getLoggedUser(): Result<User, FailureResult<GetActiveUserError>>

    fun getUserById(userId: UserId): Result<User, FailureResult<GetUserError>>
}
