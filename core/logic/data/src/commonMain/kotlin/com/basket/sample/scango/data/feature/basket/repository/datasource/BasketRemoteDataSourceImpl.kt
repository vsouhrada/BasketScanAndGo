package com.basket.sample.scango.data.feature.basket.repository.datasource

import com.basket.core.common.result.Result
import com.basket.core.common.result.failure.FailureResult
import com.basket.sample.scango.domain.error.CreateBasketError
import com.basket.sample.scango.domain.feature.basket.create.usecase.CreateBasketRequest
import com.basket.sample.scango.domain.feature.basket.create.usecase.CreateBasketResponse

class BasketRemoteDataSourceImpl : BasketRemoteDataSource {
    override suspend fun createBasket(
        request: CreateBasketRequest
    ): Result<CreateBasketResponse, FailureResult<CreateBasketError>> {
        TODO("Not yet implemented")
    }
}
