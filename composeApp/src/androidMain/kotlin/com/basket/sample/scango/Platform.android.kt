package com.basket.sample.scango

import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinApplication

actual fun configureKoin(koinApplication: KoinApplication) {
    koinApplication.apply {
        androidLogger()
        androidContext(BasketApplication.instance)
    }
}
