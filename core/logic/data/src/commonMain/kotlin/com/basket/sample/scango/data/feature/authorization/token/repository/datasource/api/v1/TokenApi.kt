package com.basket.sample.scango.data.feature.authorization.token.repository.datasource.api.v1

import com.basket.core.common.result.Result
import com.basket.core.common.result.failure.FailureResult
import com.basket.sample.scango.data.feature.authorization.token.repository.datasource.api.v1.dto.TokenInfoDto
import com.basket.sample.scango.domain.error.FetchTokenInfoError
import com.basket.sample.scango.domain.feature.authorization.usecase.FetchTokenInfoRequest

interface TokenApi {
    suspend fun refreshToken(params: FetchTokenInfoRequest): Result<TokenInfoDto, FailureResult<FetchTokenInfoError>>
}
