package com.basket.sample.scango.data.feature.user.common.repository.datasource

import com.basket.core.common.result.Result
import com.basket.core.common.result.failure.FailureResult
import com.basket.core.common.result.toSuccess
import com.basket.sample.scango.domain.common.model.User
import com.basket.sample.scango.domain.error.GetActiveUserError

class UserLocalDataSourceImpl : UserLocalDataSource {
    private lateinit var currentUser: User // TODO just for test

    override fun saveActiveUser(currentUser: User) {
        this.currentUser = currentUser
    }

    override fun getLoggedUser(): Result<User, FailureResult<GetActiveUserError>> {
        return this.currentUser.toSuccess()
    }
}
