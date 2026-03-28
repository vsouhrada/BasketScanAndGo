package com.basket.sample.scango.data.feature.authorization.token.repository.datasource

import com.basket.core.common.result.Result
import com.basket.core.common.result.failure.FailureResult
import com.basket.core.common.result.toSuccess
import com.basket.sample.scango.data.feature.authorization.token.repository.datasource.api.v1.TokenApi
import com.basket.sample.scango.data.feature.authorization.token.repository.datasource.mapper.toDomain
import com.basket.sample.scango.domain.error.FetchTokenInfoError
import com.basket.sample.scango.domain.feature.authorization.model.TokenInfo
import com.basket.sample.scango.domain.feature.authorization.usecase.FetchTokenInfoRequest

class TokenRemoteDataSourceImpl(private val tokenApi: TokenApi) : TokenRemoteDataSource {
    override suspend fun fetchTokenInfo(params: FetchTokenInfoRequest): Result<TokenInfo, FailureResult<FetchTokenInfoError>> {
        return when (val result = tokenApi.refreshToken(params = params)) {
            is Result.Success -> result.data.toDomain().toSuccess()
            is Result.Failure -> result
        }
    }
}
