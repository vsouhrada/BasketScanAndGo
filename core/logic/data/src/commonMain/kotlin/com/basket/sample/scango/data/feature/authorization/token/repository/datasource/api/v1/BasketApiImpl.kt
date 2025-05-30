package com.basket.sample.scango.data.feature.authorization.token.repository.datasource.api.v1

import com.basket.core.common.result.ErrorResult
import com.basket.core.common.result.Result
import com.basket.sample.scango.data.core.api.rest.BaseApi
import com.basket.sample.scango.data.core.api.rest.ktor.networkCall
import com.basket.sample.scango.data.core.api.rest.model.ErrorDto
import com.basket.sample.scango.data.feature.authorization.token.repository.datasource.api.v1.dto.CreateBasketRequestDto
import com.basket.sample.scango.data.feature.authorization.token.repository.datasource.api.v1.dto.CreateBasketResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.encodedPath
import io.ktor.http.path

class BasketApiImpl(
    override val client: HttpClient,
) : BaseApi(client = client), BasketApi {
    /**
     * Creates a new basket based on the provided request data.
     *
     * @param request The request data required to create a new basket.
     * @return A [Result] containing the [CreateBasketResponseDto] if the creation is successful,
     *         or an error [Throwable] if the operation fails.
     * @since 1.0.0
     */
    override suspend fun createBasket(request: CreateBasketRequestDto): Result<CreateBasketResponseDto, ErrorResult<ErrorDto>> {
        return networkCall {
            client.post {
                url {
                    encodedPath =
                        encodedPath.let { startingPath ->
                            path("/baskets")
                            "$startingPath${encodedPath.substring(1)}"
                        }
                }
                setBody(request)
            }
        }
    }
}
