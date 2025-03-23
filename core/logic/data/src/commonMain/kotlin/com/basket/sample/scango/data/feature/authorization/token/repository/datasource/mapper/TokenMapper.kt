package com.basket.sample.scango.data.feature.authorization.token.repository.datasource.mapper

import com.basket.sample.scango.data.feature.authorization.token.repository.datasource.api.model.dto.TokenInfoDto
import com.basket.sample.scango.domain.feature.authorization.model.TokenInfo

fun TokenInfoDto.toDomain() = TokenInfo(
    accessToken = accessToken,
    refreshToken = refreshToken
)
