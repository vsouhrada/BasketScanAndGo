package com.basket.sample.scango.data.common.api.providers

import com.basket.sample.scango.data.common.api.providers.model.ServerPath

class BasketApiServerPathProviderImpl : BasketApiServerPathProvider {
    override fun provideServerPath(): ServerPath {
        // Use 10.0.2.2 which is the special IP that Android emulator
        // uses to communicate with the host machine's localhost
        // This should work for Android emulator, and we'll need to ensure
        // that desktop and WASM apps can resolve this IP or use a different approach
        return ServerPath(
            //basePath = "http://localhost:8080",
            //basePath = "http://10.0.2.2:8080",
            //basePath = "http://127.0.0.1:8080",
            basePath = "http://192.168.1.129:8080",
            // basePath = "http://10.63.2.225:8080",
            //basePath = "https://jsonplaceholder.typicode.com",
            contextPath = ""
        )
    }
}
