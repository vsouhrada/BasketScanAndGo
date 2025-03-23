package com.basket.sample.scango.data.common.api.providers

import com.basket.sample.scango.data.common.api.providers.model.ServerPath

class BasketApiServerPathProviderImpl : BasketApiServerPathProvider {

    override fun provideServerPath(): ServerPath {
        return ServerPath(
            basePath = "https://basketshop.com:443/",
            contextPath = "basket-service/rest/basket/v1/"
        )
    }
}
