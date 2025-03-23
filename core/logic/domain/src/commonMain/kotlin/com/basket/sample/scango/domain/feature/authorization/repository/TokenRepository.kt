package com.basket.sample.scango.domain.feature.authorization.repository

import com.basket.core.common.result.Result
import com.basket.core.common.result.failure.FailureResult
import com.basket.sample.scango.domain.error.FetchTokenInfoError
import com.basket.sample.scango.domain.feature.authorization.model.TokenInfo

interface TokenRepository {

    suspend fun refreshAccessToken(): Result<TokenInfo, FailureResult<FetchTokenInfoError>>
}
