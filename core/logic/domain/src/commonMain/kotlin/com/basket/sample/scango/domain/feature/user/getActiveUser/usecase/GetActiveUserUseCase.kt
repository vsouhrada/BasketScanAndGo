package com.basket.sample.scango.domain.feature.user.getActiveUser.usecase

import com.basket.sample.scango.domain.common.model.User
import com.basket.sample.scango.domain.core.usecase.UseCaseNoParams
import com.basket.sample.scango.domain.error.GetActiveUserError

data class GetActiveUserResponse(
    val currentUser: User,
)

abstract class GetActiveUserUseCase : UseCaseNoParams<GetActiveUserResponse, GetActiveUserError>()
