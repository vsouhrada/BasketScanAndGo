package com.basket.sample.scango.data.feature.authorization.token.repository.datasource.api.v1

import com.basket.core.common.result.Result
import com.basket.core.common.result.failure.FailureResult
import com.basket.core.common.result.failure.toFailure
import com.basket.core.common.result.toSuccess
import com.basket.sample.scango.data.feature.authorization.token.repository.datasource.api.v1.dto.AuthRequestDto
import com.basket.sample.scango.data.feature.authorization.token.repository.datasource.api.v1.dto.TokenInfoDto
import com.basket.sample.scango.domain.error.FetchTokenInfoError
import com.basket.sample.scango.domain.error.UnexpectedError
import com.basket.sample.scango.domain.feature.authorization.usecase.FetchTokenInfoRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.encodedPath
import io.ktor.http.path

class TokenApiImpl(
    private val client: HttpClient,
) : TokenApi {
    override suspend fun refreshToken(params: FetchTokenInfoRequest): Result<TokenInfoDto, FailureResult<FetchTokenInfoError>> {
        return try {
            val response =
                client.post {
                    url {
                        encodedPath =
                            encodedPath.let { startingPath ->
                                path("/auth/login")
                                "$startingPath${encodedPath.substring(1)}"
                            }
                    }
                    setBody(
                        AuthRequestDto(
                            username = params.username,
                            password = params.password,
                        ),
                    )
                }
            response.body<TokenInfoDto>().toSuccess()
        } catch (e: Throwable) {
            e.toFailure(UnexpectedError(message = "Refresh token failure!"))
        }
    }
}
