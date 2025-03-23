package com.basket.sample.scango.data.feature.basket.repository

import com.basket.core.common.result.Result
import com.basket.core.common.result.failure.FailureResult
import com.basket.sample.scango.data.feature.basket.repository.datasource.BasketLocalDataSource
import com.basket.sample.scango.data.feature.basket.repository.datasource.BasketRemoteDataSource
import com.basket.sample.scango.domain.common.model.Basket
import com.basket.sample.scango.domain.error.CreateBasketError
import com.basket.sample.scango.domain.error.GetActiveBasketError
import com.basket.sample.scango.domain.error.ObserveActiveBasketError
import com.basket.sample.scango.domain.error.SetActiveBasketError
import com.basket.sample.scango.domain.feature.basket.active.GetActiveBasketResponse
import com.basket.sample.scango.domain.feature.basket.active.SetActiveBasketRequest
import com.basket.sample.scango.domain.feature.basket.active.SetActiveBasketResponse
import com.basket.sample.scango.domain.feature.basket.common.repository.BasketRepository
import com.basket.sample.scango.domain.feature.basket.create.usecase.CreateBasketRequest
import com.basket.sample.scango.domain.feature.basket.create.usecase.CreateBasketResponse
import kotlinx.coroutines.flow.Flow

class BasketRepositoryImpl(
    private val basketLocalDataSource: BasketLocalDataSource,
    private val basketRemoteDataSource: BasketRemoteDataSource,
) : BasketRepository {

    override suspend fun createBasket(
        request: CreateBasketRequest
    ): Result<CreateBasketResponse, FailureResult<CreateBasketError>> {
        return basketRemoteDataSource.createBasket(request)
    }

    override suspend fun observeActiveBasket(): Flow<Result<Basket, FailureResult<ObserveActiveBasketError>>> {
        return basketLocalDataSource.observeActiveBasket()
    }

    override suspend fun setActiveBasket(request: SetActiveBasketRequest): Result<SetActiveBasketResponse, FailureResult<SetActiveBasketError>> {
        return basketLocalDataSource.setActiveBasket(basket = request.basket).chainResult {
            SetActiveBasketResponse(basket = it)
        }
    }

    override suspend fun getActiveBasket(): Result<GetActiveBasketResponse, FailureResult<GetActiveBasketError>> {
        return basketLocalDataSource.getActiveBasket().chainResult {
            GetActiveBasketResponse(basket = it)
        }
    }
}
