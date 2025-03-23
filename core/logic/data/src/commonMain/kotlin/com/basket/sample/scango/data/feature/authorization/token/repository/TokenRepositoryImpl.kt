package com.basket.sample.scango.data.feature.authorization.token.repository

import com.basket.core.common.klogger.getKLogger
import com.basket.core.common.result.Result
import com.basket.core.common.result.failure.FailureResult
import com.basket.sample.scango.data.feature.authorization.token.repository.datasource.TokenLocalDataSource
import com.basket.sample.scango.data.feature.authorization.token.repository.datasource.TokenRemoteDataSource
import com.basket.sample.scango.domain.error.FetchTokenInfoError
import com.basket.sample.scango.domain.feature.authorization.model.TokenInfo
import com.basket.sample.scango.domain.feature.authorization.repository.TokenRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TokenRepositoryImpl(
    private val tokenLocalDataSource: TokenLocalDataSource,
    private val tokenRemoteDataSource: TokenRemoteDataSource
) : TokenRepository {

    private val logger = getKLogger { }

    override suspend fun refreshAccessToken(): Result<TokenInfo, FailureResult<FetchTokenInfoError>> {
        return withContext(Dispatchers.Default) {
            val localTokenResult = tokenLocalDataSource.getTokenInfo()
            if (localTokenResult.getOrNull() == null) {
                when (val tokenRemoteResult = tokenRemoteDataSource.fetchTokenInfo()) {
                    is Result.Success -> {
                        tokenLocalDataSource.setTokenInfo(tokenRemoteResult.data)
                        tokenRemoteResult
                    }

                    is Result.Failure -> {
                        logger.error { tokenRemoteResult }
                        tokenRemoteResult
                    }
                }
            } else {
                localTokenResult
            }
        }
    }
}
