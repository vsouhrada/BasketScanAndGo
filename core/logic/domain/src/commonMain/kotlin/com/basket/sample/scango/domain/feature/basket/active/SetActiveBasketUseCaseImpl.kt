package com.basket.sample.scango.domain.feature.basket.active

import com.basket.core.common.result.Result
import com.basket.core.common.result.failure.FailureResult
import com.basket.sample.scango.domain.error.SetActiveBasketError
import com.basket.sample.scango.domain.feature.basket.common.repository.BasketRepository

class SetActiveBasketUseCaseImpl(
    private val basketRepository: BasketRepository,
) : SetActiveBasketUseCase() {
    override suspend fun doWork(
        params: SetActiveBasketRequest
    ): Result<SetActiveBasketResponse, FailureResult<SetActiveBasketError>> {
        return basketRepository.setActiveBasket(request = params)
    }
}
