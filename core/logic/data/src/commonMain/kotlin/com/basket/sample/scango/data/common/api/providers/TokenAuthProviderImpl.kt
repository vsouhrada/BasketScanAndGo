package com.basket.sample.scango.data.common.api.providers

import com.basket.sample.scango.domain.feature.authorization.model.TokenInfo
import com.basket.sample.scango.domain.feature.authorization.usecase.FetchTokenInfoUseCase
import com.basket.core.common.result.Result

class TokenAuthProviderImpl(/*private val fetchTokenInfo: FetchTokenInfoUseCase*/) : TokenAuthProvider {

    override suspend fun getAccessToken(): TokenInfo {
        return getTokenInfo()
    }

    override suspend fun refreshToken(): TokenInfo {
        return getTokenInfo()
    }

    private suspend fun getTokenInfo(): TokenInfo {
        /*return when (val result = fetchTokenInfo()) {
            is Result.Success -> result.data.tokenInfo
            is Result.Failure -> {
                // TODO() - add error here
                TokenInfo(accessToken = "", refreshToken = "")
            }
        }*/
        return TokenInfo(accessToken = "", refreshToken = "")
    }
}
