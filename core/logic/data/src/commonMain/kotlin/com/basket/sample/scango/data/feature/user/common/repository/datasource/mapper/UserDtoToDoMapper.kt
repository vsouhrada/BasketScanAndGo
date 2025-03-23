package com.basket.sample.scango.data.feature.user.common.repository.datasource.mapper

import com.basket.sample.scango.data.feature.authorization.token.repository.datasource.api.v1.dto.UserDto
import com.basket.sample.scango.domain.common.model.User

interface UserDtoToDoMapper {
    fun mapUserDto(dto: UserDto): User
}
