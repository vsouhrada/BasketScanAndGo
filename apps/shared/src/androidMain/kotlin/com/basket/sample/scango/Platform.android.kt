package com.basket.sample.scango

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinApplication

object AndroidContextHolder {
    lateinit var context: Context
}

actual fun configureKoin(koinApplication: KoinApplication) {
    koinApplication.apply {
        androidLogger()
        androidContext(AndroidContextHolder.context)
    }
}
