package com.basket.sample.scango.domain.feature.user.getActiveUser.usecase

import com.basket.core.common.result.Result
import com.basket.core.common.result.failure.FailureResult
import com.basket.sample.scango.domain.error.GetActiveUserError
import com.basket.sample.scango.domain.feature.user.common.repository.UserRepository

class GetActiveUserUseCaseImpl(
    private val userRepository: UserRepository
) : GetActiveUserUseCase() {

    override suspend fun doWork(params: Unit): Result<GetActiveUserResponse, FailureResult<GetActiveUserError>> {
        return userRepository.getLoggedUser().chainResult { user ->
            GetActiveUserResponse(
                currentUser = user,
            )
        }
    }
}
