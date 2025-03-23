package com.basket.sample.scango.domain.feature.user.getUser.usecase

import com.basket.sample.scango.domain.common.model.User
import com.basket.sample.scango.domain.common.model.UserId
import com.basket.sample.scango.domain.core.usecase.UseCase
import com.basket.sample.scango.domain.error.GetUserError

data class GetUserRequest(
    val userId: UserId,
)

data class GetUserResponse(
    val user: User,
)

abstract class GetUserUseCase : UseCase<GetUserResponse, GetUserError, GetUserRequest>()
