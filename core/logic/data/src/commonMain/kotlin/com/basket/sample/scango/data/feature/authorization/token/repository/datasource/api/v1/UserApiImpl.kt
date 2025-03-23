package com.basket.sample.scango.data.feature.authorization.token.repository.datasource.api.v1

import com.basket.core.common.result.ErrorResult
import com.basket.core.common.result.Result
import com.basket.sample.scango.data.core.api.rest.BaseApi
import com.basket.sample.scango.data.core.api.rest.ktor.networkCall
import com.basket.sample.scango.data.core.api.rest.model.ErrorDto
import com.basket.sample.scango.data.feature.authorization.token.repository.datasource.api.v1.dto.UserDto
import com.basket.sample.scango.domain.common.model.UserId
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.http.encodedPath
import io.ktor.http.path

class UserApiImpl(
    override val client: HttpClient,
) : BaseApi(client), UserApi {

    override suspend fun getUserById(userId: UserId): Result<UserDto, ErrorResult<ErrorDto>> {
        return networkCall {
            client.get {
                url {
                    encodedPath =
                        encodedPath.let { startingPath ->
                            path("/users/$userId")
                            "$startingPath${encodedPath.substring(1)}"
                        }
                }
            }
        }
    }
}
