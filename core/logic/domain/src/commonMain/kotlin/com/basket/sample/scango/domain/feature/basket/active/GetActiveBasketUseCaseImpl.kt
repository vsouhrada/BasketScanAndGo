package com.basket.sample.scango.domain.feature.basket.active

import com.basket.core.common.result.Result
import com.basket.core.common.result.failure.FailureResult
import com.basket.sample.scango.domain.error.GetActiveBasketError
import com.basket.sample.scango.domain.feature.basket.common.repository.BasketRepository

class GetActiveBasketUseCaseImpl(
    val basketRepository: BasketRepository,
) : GetActiveBasketUseCase() {
    override suspend fun doWork(params: Unit): Result<GetActiveBasketResponse, FailureResult<GetActiveBasketError>> {
        return basketRepository.getActiveBasket()
    }
}
