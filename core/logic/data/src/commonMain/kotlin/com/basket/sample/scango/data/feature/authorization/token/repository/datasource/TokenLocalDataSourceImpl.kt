package com.basket.sample.scango.data.feature.authorization.token.repository.datasource

import com.basket.core.common.result.Result
import com.basket.core.common.result.failure.FailureResult
import com.basket.core.common.result.toSuccessIfNotNull
import com.basket.sample.scango.domain.error.FetchTokenInfoError
import com.basket.sample.scango.domain.error.UnexpectedError
import com.basket.sample.scango.domain.feature.authorization.model.TokenInfo

class TokenLocalDataSourceImpl : TokenLocalDataSource {
    private var cachedTokenInfo: TokenInfo? = null

    override suspend fun getTokenInfo(): Result<TokenInfo, FailureResult<FetchTokenInfoError>> {
        return cachedTokenInfo.toSuccessIfNotNull {
            FailureResult(UnexpectedError(message = "No token found"))
        }
    }

    override suspend fun setTokenInfo(tokenInfo: TokenInfo) {
        cachedTokenInfo = tokenInfo
    }
}
