package com.basket.sample.scango.data.feature.user.common.repository

import com.basket.core.common.result.Result
import com.basket.core.common.result.failure.FailureResult
import com.basket.sample.scango.data.feature.user.common.repository.datasource.UserLocalDataSource
import com.basket.sample.scango.data.feature.user.common.repository.datasource.UserRemoteDataSource
import com.basket.sample.scango.domain.common.model.User
import com.basket.sample.scango.domain.common.model.UserId
import com.basket.sample.scango.domain.error.GetActiveUserError
import com.basket.sample.scango.domain.error.GetUserError
import com.basket.sample.scango.domain.feature.user.common.repository.UserRepository

class UserRepositoryImpl(
    private val localDataSource: UserLocalDataSource,
    private val remoteDataSource: UserRemoteDataSource,
) : UserRepository {
    override fun saveActiveUser(currentUser: User) {
        return localDataSource.saveActiveUser(currentUser)
    }

    override fun getLoggedUser(): Result<User, FailureResult<GetActiveUserError>> {
        return localDataSource.getLoggedUser()
    }

    override suspend fun getUserById(userId: UserId): Result<User, FailureResult<GetUserError>> {
        return remoteDataSource.getUserById(userId)
    }
}
