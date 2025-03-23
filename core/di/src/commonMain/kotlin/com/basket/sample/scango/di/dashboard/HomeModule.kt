package com.basket.sample.scango.di.dashboard

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import com.basket.sample.scango.presentation.feature.home.viewmodel.HomeViewModel

val homeModule = module {

    viewModelOf(::HomeViewModel)
}
