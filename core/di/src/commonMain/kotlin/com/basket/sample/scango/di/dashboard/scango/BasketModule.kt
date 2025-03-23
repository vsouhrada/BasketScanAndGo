package com.basket.sample.scango.di.dashboard.scango

import com.basket.sample.scango.presentation.feature.scango.basket.overview.viewmodel.BasketViewModel
import com.basket.sample.scango.presentation.feature.scango.basket.create.viewmodel.CreateBasketViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val basketModule = module {

    viewModelOf(::BasketViewModel)

    viewModelOf(::CreateBasketViewModel)
}
