package com.basket.sample.scango.data.feature.authorization.token.repository.datasource.api

import com.basket.core.common.result.Result
import com.basket.core.common.result.failure.FailureResult
import com.basket.sample.scango.data.feature.authorization.token.repository.datasource.api.model.dto.TokenInfoDto
import com.basket.sample.scango.domain.error.FetchTokenInfoError

interface TokenApi {

    suspend fun refreshToken(): Result<TokenInfoDto, FailureResult<FetchTokenInfoError>>
}
