package com.basket.sample.scango.domain.error

import com.basket.sample.scango.domain.common.model.Problem

sealed interface GetUserError : BasketBaseError

data class UserNotFound(
    override val problem: Problem? = null,
    override val message: String = "User not found!",
) : GetUserError
