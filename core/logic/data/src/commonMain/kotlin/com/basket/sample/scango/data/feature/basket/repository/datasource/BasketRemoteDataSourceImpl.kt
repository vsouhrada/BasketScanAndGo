package com.basket.sample.scango.data.feature.basket.repository.datasource

import com.basket.core.common.result.Result
import com.basket.core.common.result.failure.FailureResult
import com.basket.core.common.result.toSuccess
import com.basket.sample.scango.data.common.mapper.toDomain
import com.basket.sample.scango.data.common.mapper.toFailureResult
import com.basket.sample.scango.data.feature.authorization.token.repository.datasource.api.v1.BasketApi
import com.basket.sample.scango.data.feature.basket.repository.datasource.mapper.BasketMapper
import com.basket.sample.scango.domain.error.CreateBasketError
import com.basket.sample.scango.domain.error.CreateBasketFailed
import com.basket.sample.scango.domain.feature.basket.create.usecase.CreateBasketRequest
import com.basket.sample.scango.domain.feature.basket.create.usecase.CreateBasketResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class BasketRemoteDataSourceImpl(
    private val basketApi: BasketApi,
    private val ioDispatcher: CoroutineDispatcher,
    private val basketMapper: BasketMapper,
) : BasketRemoteDataSource {
    override suspend fun createBasket(
        request: CreateBasketRequest
    ): Result<CreateBasketResponse, FailureResult<CreateBasketError>> {
        return withContext(ioDispatcher) {
            when (val response =
                basketApi.createBasket(request = basketMapper.mapCreateBasketRequestDoToDto(request))) {
                is Result.Success -> {
                    CreateBasketResponse(
                        basket = basketMapper.mapBasketDtoToDomain(response.data.basket)
                    ).toSuccess()
                }

                is Result.Failure -> response.toFailureResult(
                    error =
                        CreateBasketFailed(
                            problem = response.error.error.problem.toDomain(),
                        ),
                )
            }
        }
    }
}
