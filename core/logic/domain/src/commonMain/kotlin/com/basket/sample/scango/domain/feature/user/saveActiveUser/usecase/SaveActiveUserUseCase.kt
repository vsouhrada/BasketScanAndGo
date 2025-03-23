package com.basket.sample.scango.domain.feature.user.saveActiveUser.usecase

import com.basket.sample.scango.domain.common.model.User
import com.basket.sample.scango.domain.core.usecase.UseCase
import com.basket.sample.scango.domain.error.SaveActiveUserError

data class SaveActiveUserRequest(
    val currentUser: User,
)

abstract class SaveActiveUserUseCase :
    UseCase<Unit, SaveActiveUserError, SaveActiveUserRequest>()
