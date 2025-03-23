package com.basket.sample.scango.data.feature.authorization.token.repository.datasource

import com.basket.core.common.result.Result
import com.basket.core.common.result.failure.FailureResult
import com.basket.sample.scango.domain.error.FetchTokenInfoError
import com.basket.sample.scango.domain.feature.authorization.model.TokenInfo

interface TokenRemoteDataSource {
    suspend fun fetchTokenInfo(): Result<TokenInfo, FailureResult<FetchTokenInfoError>>
}
