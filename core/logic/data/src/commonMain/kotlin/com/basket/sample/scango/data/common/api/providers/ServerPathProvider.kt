package com.basket.sample.scango.data.common.api.providers

import com.basket.sample.scango.data.common.api.providers.model.ServerPath

interface ServerPathProvider {
    fun provideServerPath(): ServerPath
}
