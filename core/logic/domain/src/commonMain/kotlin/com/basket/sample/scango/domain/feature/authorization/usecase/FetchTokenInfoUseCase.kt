package com.basket.sample.scango.domain.feature.authorization.usecase

import com.basket.sample.scango.domain.core.usecase.UseCase
import com.basket.sample.scango.domain.error.FetchTokenInfoError
import com.basket.sample.scango.domain.feature.authorization.model.TokenInfo

data class FetchTokenInfoRequest(
    val username: String,
    val password: String,
)

data class FetchTokenInfoResponse(
    val tokenInfo: TokenInfo,
)

abstract class FetchTokenInfoUseCase : UseCase<FetchTokenInfoResponse, FetchTokenInfoError, FetchTokenInfoRequest>()
