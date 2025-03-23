package com.basket.sample.scango.data.feature.user.common.repository.datasource

import com.basket.core.common.result.Result
import com.basket.core.common.result.failure.FailureResult
import com.basket.sample.scango.domain.common.model.User
import com.basket.sample.scango.domain.error.GetActiveUserError

interface UserLocalDataSource {

    fun saveActiveUser(currentUser: User)

    fun getLoggedUser(): Result<User, FailureResult<GetActiveUserError>>
}
