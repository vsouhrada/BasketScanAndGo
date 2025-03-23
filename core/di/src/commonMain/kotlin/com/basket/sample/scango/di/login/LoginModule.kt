package com.basket.sample.scango.di.login

import com.basket.sample.scango.presentation.feature.login.viewmodel.LoginViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val loginModule =
    module {

        viewModelOf(::LoginViewModel)
    }
