package com.basket.sample.scango.domain.feature.user.saveActiveUser.usecase

import com.basket.core.common.result.Result
import com.basket.core.common.result.failure.FailureResult
import com.basket.core.common.result.toSuccess
import com.basket.sample.scango.domain.error.SaveActiveUserError
import com.basket.sample.scango.domain.feature.user.common.repository.UserRepository

class SaveActiveUserUseCaseImpl(
    private val userRepository: UserRepository,
) : SaveActiveUserUseCase() {
    override suspend fun doWork(params: SaveActiveUserRequest): Result<Unit, FailureResult<SaveActiveUserError>> {
        return userRepository.saveActiveUser(params.currentUser).toSuccess()
    }
}
