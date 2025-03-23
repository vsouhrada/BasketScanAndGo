package com.basket.sample.scango.domain.feature.basket.create.usecase

import com.basket.core.common.result.Result
import com.basket.core.common.result.failure.FailureResult
import com.basket.sample.scango.domain.error.CreateBasketError
import com.basket.sample.scango.domain.feature.basket.common.repository.BasketRepository

class CreateBasketUseCaseImpl(
    private val basketRepository: BasketRepository,
) : CreateBasketUseCase() {
    override suspend fun doWork(
        params: CreateBasketRequest
    ): Result<CreateBasketResponse, FailureResult<CreateBasketError>> {
        return basketRepository.createBasket(request = params)
    }
}
