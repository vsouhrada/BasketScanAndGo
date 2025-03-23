package com.basket.sample.scango.data.feature.basket.repository.datasource

import com.basket.core.common.result.Result
import com.basket.core.common.result.failure.FailureResult
import com.basket.sample.scango.domain.common.model.Basket
import com.basket.sample.scango.domain.error.GetActiveBasketError
import com.basket.sample.scango.domain.error.ObserveActiveBasketError
import com.basket.sample.scango.domain.error.SetActiveBasketError
import kotlinx.coroutines.flow.Flow

interface BasketLocalDataSource {

    suspend fun observeActiveBasket(): Flow<Result<Basket, FailureResult<ObserveActiveBasketError>>>

    suspend fun setActiveBasket(basket: Basket): Result<Basket, FailureResult<SetActiveBasketError>>

    suspend fun getActiveBasket(): Result<Basket, FailureResult<GetActiveBasketError>>
}
