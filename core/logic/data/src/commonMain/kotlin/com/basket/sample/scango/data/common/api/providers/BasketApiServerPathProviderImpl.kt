package com.basket.sample.scango.data.common.api.providers

import com.basket.sample.scango.data.common.api.providers.model.ServerPath

class BasketApiServerPathProviderImpl : BasketApiServerPathProvider {
    private companion object {
        private const val SERVER_BASE_PATH = "http://192.168.1.138:8080"
    }

    override fun provideServerPath(): ServerPath {
        return ServerPath(
            basePath = SERVER_BASE_PATH,
            contextPath = "",
        )
    }
}
