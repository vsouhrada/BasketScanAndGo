package com.basket.sample.scango.domain.feature.user.getUser.usecase

import com.basket.sample.scango.domain.core.usecase.UseCase
import com.basket.sample.scango.domain.error.GetUserError
import com.basket.sample.scango.domain.feature.user.common.model.User
import com.basket.sample.scango.domain.feature.user.common.model.UserId

data class GetUserRequest(
    val userId: UserId,
)

data class GetUserResponse(
    val user: User,
)

abstract class GetUserUseCase : UseCase<GetUserResponse, GetUserError, GetUserRequest>()
