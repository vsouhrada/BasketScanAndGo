package com.basket.sample.scango.domain.feature.user.getUser.usecase

import com.basket.core.common.result.Result
import com.basket.core.common.result.failure.FailureResult
import com.basket.sample.scango.domain.error.GetUserError
import com.basket.sample.scango.domain.feature.user.common.repository.UserRepository

class GetUserUseCaseImpl(
    private val userRepository: UserRepository,
) : GetUserUseCase() {
    override suspend fun doWork(params: GetUserRequest): Result<GetUserResponse, FailureResult<GetUserError>> {
        return userRepository.getUserById(userId = params.userId).chainResult {
            GetUserResponse(
                user = it,
            )
        }
    }
}
