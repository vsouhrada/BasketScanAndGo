package com.basket.sample.scango.data.feature.basket.repository.datasource

import com.basket.core.common.result.Result
import com.basket.core.common.result.failure.FailureResult
import com.basket.core.common.result.failure.createFailureResult
import com.basket.core.common.result.toSuccess
import com.basket.sample.scango.domain.common.model.Basket
import com.basket.sample.scango.domain.error.ActiveBasketNotFound
import com.basket.sample.scango.domain.error.GetActiveBasketError
import com.basket.sample.scango.domain.error.ObserveActiveBasketError
import com.basket.sample.scango.domain.error.SetActiveBasketError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext

class BasketLocalDataSourceImpl : BasketLocalDataSource {

    private val basketFlowCache: MutableStateFlow<Basket?> = MutableStateFlow(null)

    override suspend fun observeActiveBasket(): Flow<Result<Basket, FailureResult<ObserveActiveBasketError>>> {
        return withContext(Dispatchers.Default) {
            basketFlowCache.map { activeBasket ->
                when (activeBasket) {
                    null -> createFailureResult(error = ActiveBasketNotFound)
                    else -> activeBasket.toSuccess()
                }
            }
        }
    }

    override suspend fun setActiveBasket(basket: Basket): Result<Basket, FailureResult<SetActiveBasketError>> {
        return withContext(Dispatchers.Default) {
            basketFlowCache.update {
                basket
            }
            basket.toSuccess()
        }
    }

    override suspend fun getActiveBasket(): Result<Basket, FailureResult<GetActiveBasketError>> {
        return withContext(Dispatchers.Default) {
            basketFlowCache.value?.toSuccess() ?: createFailureResult(error = ActiveBasketNotFound)
        }
    }
}
