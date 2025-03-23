package com.basket.sample.scango

import android.app.Application

class BasketApplication : Application() {

    companion object {
        lateinit var instance: BasketApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
