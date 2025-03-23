package com.basket.sample.scango.domain.feature.authorization.usecase

import com.basket.core.common.result.Result
import com.basket.core.common.result.failure.FailureResult
import com.basket.sample.scango.domain.error.FetchTokenInfoError
import com.basket.sample.scango.domain.feature.authorization.repository.TokenRepository

class FetchTokenUserInfoUseCaseImpl(
    private val tokenRepository: TokenRepository,
) : FetchTokenInfoUseCase() {
    override suspend fun doWork(params: Unit): Result<FetchTokenInfoResponse, FailureResult<FetchTokenInfoError>> {
        return tokenRepository.refreshAccessToken().chainResult {
            FetchTokenInfoResponse(tokenInfo = it)
        }
    }
}
