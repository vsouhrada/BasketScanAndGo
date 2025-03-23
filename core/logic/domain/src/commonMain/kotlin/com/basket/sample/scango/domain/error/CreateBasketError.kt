package com.basket.sample.scango.domain.error

import com.basket.sample.scango.domain.common.model.Problem

sealed interface CreateBasketError : BasketBaseError

data class CreateBasketFailed(
    override val problem: Problem? = null,
    override val message: String = "User not found!",
) : CreateBasketError
