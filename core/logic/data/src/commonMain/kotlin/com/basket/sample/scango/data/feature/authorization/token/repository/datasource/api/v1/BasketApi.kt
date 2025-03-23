package com.basket.sample.scango.data.feature.authorization.token.repository.datasource.api.v1

import com.basket.core.common.result.Result
import com.basket.sample.scango.data.feature.authorization.token.repository.datasource.api.v1.dto.CreateBasketRequestDto
import com.basket.sample.scango.data.feature.authorization.token.repository.datasource.api.v1.dto.CreateBasketResponseDto

interface BasketApi {

    suspend fun createBasket(request: CreateBasketRequestDto): Result<CreateBasketResponseDto, Throwable>
}
