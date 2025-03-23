package com.basket.sample.scango.domain.feature.authorization.usecase

import com.basket.sample.scango.domain.core.usecase.UseCaseNoParams
import com.basket.sample.scango.domain.error.FetchTokenInfoError
import com.basket.sample.scango.domain.feature.authorization.model.TokenInfo

data class FetchTokenInfoResponse(
    val tokenInfo: TokenInfo,
)

abstract class FetchTokenInfoUseCase : UseCaseNoParams<FetchTokenInfoResponse, FetchTokenInfoError>()
