package com.basket.sample.scango.domain.feature.basket.common.repository

import com.basket.core.common.result.Result
import com.basket.core.common.result.failure.FailureResult
import com.basket.sample.scango.domain.common.model.Basket
import com.basket.sample.scango.domain.error.CreateBasketError
import com.basket.sample.scango.domain.error.GetActiveBasketError
import com.basket.sample.scango.domain.error.ObserveActiveBasketError
import com.basket.sample.scango.domain.error.SetActiveBasketError
import com.basket.sample.scango.domain.feature.basket.active.GetActiveBasketResponse
import com.basket.sample.scango.domain.feature.basket.active.SetActiveBasketRequest
import com.basket.sample.scango.domain.feature.basket.active.SetActiveBasketResponse
import com.basket.sample.scango.domain.feature.basket.create.usecase.CreateBasketRequest
import com.basket.sample.scango.domain.feature.basket.create.usecase.CreateBasketResponse
import kotlinx.coroutines.flow.Flow

interface BasketRepository {
    suspend fun createBasket(
        request: CreateBasketRequest
    ): Result<CreateBasketResponse, FailureResult<CreateBasketError>>

    suspend fun observeActiveBasket(): Flow<Result<Basket, FailureResult<ObserveActiveBasketError>>>

    suspend fun setActiveBasket(
        request: SetActiveBasketRequest
    ): Result<SetActiveBasketResponse, FailureResult<SetActiveBasketError>>

    suspend fun getActiveBasket(): Result<GetActiveBasketResponse, FailureResult<GetActiveBasketError>>
}
