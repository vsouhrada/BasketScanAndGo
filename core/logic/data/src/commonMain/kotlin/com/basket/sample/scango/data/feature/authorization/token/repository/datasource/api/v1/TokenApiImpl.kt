package com.basket.sample.scango.data.feature.authorization.token.repository.datasource.api.v1

import com.basket.core.common.result.Result
import com.basket.core.common.result.failure.FailureResult
import com.basket.core.common.result.failure.toFailure
import com.basket.core.common.result.toSuccess
import com.basket.sample.scango.data.feature.authorization.token.repository.datasource.api.v1.dto.TokenInfoDto
import com.basket.sample.scango.domain.error.FetchTokenInfoError
import com.basket.sample.scango.domain.error.UnexpectedError
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.submitForm
import io.ktor.http.Parameters
import io.ktor.http.encodedPath
import io.ktor.http.path

class TokenApiImpl(
    private val client: HttpClient,
) : TokenApi {

    override suspend fun refreshToken(): Result<TokenInfoDto, FailureResult<FetchTokenInfoError>> {
        // TODO add username and password as property for refreshing token!!!
        return try {
            val response = client.submitForm(
                formParameters = Parameters.build {
                    append("username", "jsnow")
                    append("password", "123")
                    append("grant_type", "password")
                }
            ) {
                url {
                    encodedPath = encodedPath.let { startingPath ->
                        path("/auth/login")
                        "$startingPath${encodedPath.substring(1)}"
                    }
                }
            }
            response.body<TokenInfoDto>().toSuccess()
        } catch (e: Throwable) {
            e.toFailure(UnexpectedError(message = "Refresh token failure!"))
        }
    }
}
