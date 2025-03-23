package com.basket.sample.scango.data.feature.authorization.token.repository.datasource.api.v1

import com.basket.core.common.result.ErrorResult
import com.basket.core.common.result.Result
import com.basket.sample.scango.data.core.api.rest.model.ErrorDto
import com.basket.sample.scango.data.feature.authorization.token.repository.datasource.api.v1.dto.UserDto
import com.basket.sample.scango.domain.common.model.UserId

interface UserApi {
    suspend fun getUserById(userId: UserId): Result<UserDto, ErrorResult<ErrorDto>>
}
