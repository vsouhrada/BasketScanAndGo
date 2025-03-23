package com.basket.sample.scango.di

import org.koin.dsl.module

val allModules = module {
    includes(
        allAppModules
    )
}
