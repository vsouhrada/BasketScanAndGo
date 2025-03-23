package com.basket.sample.scango.presentation.feature.login.screen.state

import com.basket.sample.scango.presentation.feature.login.model.UserCredentials

sealed class LoginScreenEvent {
    data class AuthorizeUser(val userCredentials: UserCredentials) : LoginScreenEvent()

    data object RegisterNewUser : LoginScreenEvent()
}
