package com.basket.sample.scango

import android.app.Application

class BasketApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidContextHolder.context = this
    }
}
