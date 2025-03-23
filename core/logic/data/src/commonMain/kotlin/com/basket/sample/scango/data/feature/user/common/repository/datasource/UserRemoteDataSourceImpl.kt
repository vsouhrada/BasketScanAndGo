package com.basket.sample.scango.data.feature.user.common.repository.datasource

import com.basket.core.common.result.Result
import com.basket.core.common.result.failure.FailureResult
import com.basket.core.common.result.failure.toFailure
import com.basket.core.common.result.toSuccess
import com.basket.sample.scango.data.common.mapper.toDomain
import com.basket.sample.scango.data.common.mapper.toFailureResult
import com.basket.sample.scango.data.feature.authorization.token.repository.datasource.api.v1.UserApi
import com.basket.sample.scango.data.feature.user.common.repository.datasource.mapper.UserDtoToDoMapper
import com.basket.sample.scango.domain.common.model.User
import com.basket.sample.scango.domain.common.model.UserId
import com.basket.sample.scango.domain.error.GetUserError
import com.basket.sample.scango.domain.error.UserNotFound
import com.basket.sample.scango.domain.error.createMissingRequiredDataError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class UserRemoteDataSourceImpl(
    private val userApi: UserApi,
    private val dtoMapper: UserDtoToDoMapper,
    private val ioDispatcher: CoroutineDispatcher,
) : UserRemoteDataSource {

    override suspend fun getUserById(userId: UserId): Result<User, FailureResult<GetUserError>> {
        return withContext(ioDispatcher) {
            when (val response = userApi.getUserById(userId)) {
                is Result.Success -> {
                    try {
                        dtoMapper.mapUserDto(response.data).toSuccess()
                    } catch (e: IllegalArgumentException) {
                        e.toFailure(createMissingRequiredDataError())
                    }
                }

                is Result.Failure -> response.toFailureResult(
                    error = UserNotFound(
                        problem = response.error.error.problem.toDomain(),
                    )
                )
            }
        }
    }
}
