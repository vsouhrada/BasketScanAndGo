package com.basket.sample.scango.data.feature.user.common.repository.datasource.mapper

import com.basket.sample.scango.data.feature.authorization.token.repository.datasource.api.v1.dto.UserDto
import com.basket.sample.scango.domain.common.model.User

class UserDtoToDoMapperImpl : UserDtoToDoMapper {
    override fun mapUserDto(dto: UserDto) = User(
        id = dto.userId,
        username = dto.username,
        email = dto.email,
        firstName = dto.firstName,
        lastName = dto.lastName,
        profilePictureUrl = dto.profilePictureUrl,
    )
}
