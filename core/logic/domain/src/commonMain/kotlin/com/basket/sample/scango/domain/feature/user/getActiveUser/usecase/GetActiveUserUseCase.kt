package com.basket.sample.scango.domain.feature.user.getActiveUser.usecase

import com.basket.sample.scango.domain.core.usecase.UseCaseNoParams
import com.basket.sample.scango.domain.error.GetActiveUserError
import com.basket.sample.scango.domain.feature.user.common.model.User

data class GetActiveUserResponse(
    val currentUser: User,
)

abstract class GetActiveUserUseCase : UseCaseNoParams<GetActiveUserResponse, GetActiveUserError>()
