package com.basket.sample.scango.domain.feature.basket.active

import com.basket.core.common.result.Result
import com.basket.core.common.result.failure.FailureResult
import com.basket.core.common.result.toSuccess
import com.basket.sample.scango.domain.error.ObserveActiveBasketError
import com.basket.sample.scango.domain.feature.basket.common.repository.BasketRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ObserveActiveBasketUseCaseImpl(
    private val basketRepository: BasketRepository
) : ObserveActiveBasketUseCase() {

    override suspend fun doWork(
        params: Unit
    ): Flow<Result<ObserveActiveBasketResponse, FailureResult<ObserveActiveBasketError>>> {
        return basketRepository.observeActiveBasket().map { result ->
            when (result) {
                is Result.Success -> ObserveActiveBasketResponse(basket = result.data).toSuccess()
                is Result.Failure -> result
            }
        }
    }
}
