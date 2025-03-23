package com.basket.sample.scango.data.feature.basket.repository.datasource.api

import com.basket.core.common.result.Result
import com.basket.sample.scango.data.feature.basket.repository.datasource.api.model.CreateBasketRequestDto
import com.basket.sample.scango.data.feature.basket.repository.datasource.api.model.CreateBasketResponseDto
import io.ktor.client.HttpClient

class BasketApiImpl(
    private val client: HttpClient,
) : BasketApi {

    /**
     * Creates a new basket based on the provided request data.
     *
     * @param request The request data required to create a new basket.
     * @return A [Result] containing the [CreateBasketResponseDto] if the creation is successful,
     *         or an error [Throwable] if the operation fails.
     * @since 1.0.0
     */
    override suspend fun createBasket(request: CreateBasketRequestDto): Result<CreateBasketResponseDto, Throwable> {
        TODO("Not yet implemented")
    }
}
