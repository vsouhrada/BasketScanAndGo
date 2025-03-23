package com.basket.sample.scango.di.dashboard.scango

import com.basket.sample.scango.presentation.feature.scango.startTrip.viewmodel.StartTripViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val startTripModule = module {

    viewModelOf(::StartTripViewModel)
}
