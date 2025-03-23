package com.basket.sample.scango.presentation.feature.login.screen.state

sealed class LoginScreenActionState {

    data object UserAuthorized : LoginScreenActionState()
}
