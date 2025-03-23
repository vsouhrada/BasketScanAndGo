package com.basket.sample.scango.presentation.feature.login.model

import com.basket.core.common.designSystem.uikit.screen.DataModel
import com.basket.core.common.designSystem.uikit.widget.Field
import com.basket.sample.scango.domain.common.model.UserId

data class LoginVo(
    val email: Field<String>,
    val password: Field<String>,
) : DataModel()

data class UserCredentials(
    val userId: UserId,
    val password: String,
)
