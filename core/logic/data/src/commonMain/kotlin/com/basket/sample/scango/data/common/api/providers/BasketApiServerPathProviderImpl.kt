package com.basket.sample.scango.data.common.api.providers

import com.basket.sample.scango.data.common.api.providers.model.ServerPath

class BasketApiServerPathProviderImpl : BasketApiServerPathProvider {
    override fun provideServerPath(): ServerPath {
        return ServerPath(
            basePath = "http://localhost:8080",
            contextPath = "",
        )
    }
}
