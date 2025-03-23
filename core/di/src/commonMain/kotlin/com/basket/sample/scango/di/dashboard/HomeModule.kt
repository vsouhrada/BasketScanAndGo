package com.basket.sample.scango.di.dashboard

import com.basket.sample.scango.presentation.feature.home.viewmodel.HomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val homeModule =
    module {

        viewModelOf(::HomeViewModel)
    }
