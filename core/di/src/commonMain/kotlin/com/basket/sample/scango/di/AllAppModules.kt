package com.basket.sample.scango.di

import com.basket.sample.scango.di.basket.basketModule
import com.basket.sample.scango.di.common.userModule
import com.basket.sample.scango.di.dashboard.allDashboardModules
import com.basket.sample.scango.di.dashboard.scango.allScanGoModules
import com.basket.sample.scango.di.login.loginModule
import commonModule

val allAppModules =
    listOf(
        loginModule,
        userModule,
        commonModule,
        networkModule,
        basketModule,
    ) + allDashboardModules + allScanGoModules
