package com.basket.sample.scango.data.feature.basket.repository.datasource.api

import com.basket.core.common.result.Result
import com.basket.sample.scango.data.feature.basket.repository.datasource.api.model.CreateBasketRequestDto
import com.basket.sample.scango.data.feature.basket.repository.datasource.api.model.CreateBasketResponseDto

interface BasketApi {

    suspend fun createBasket(request: CreateBasketRequestDto): Result<CreateBasketResponseDto, Throwable>
}
