package com.basket.sample.scango.presentation.feature.login.model

import com.basket.core.common.designSystem.uikit.screen.DataModel
import com.basket.core.common.designSystem.uikit.widget.Field

data class LoginVo(
    val email: Field<String>,
    val password: Field<String>,
) : DataModel()

data class UserCredentials(
    val username: String,
    val password: String,
)
