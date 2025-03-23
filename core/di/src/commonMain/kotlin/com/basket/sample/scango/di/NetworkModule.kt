package com.basket.sample.scango.di

import com.basket.sample.scango.data.common.api.providers.BasketApiServerPathProvider
import com.basket.sample.scango.data.common.api.providers.BasketApiServerPathProviderImpl
import com.basket.sample.scango.data.common.api.providers.ServerPathProvider
import com.basket.sample.scango.data.common.api.providers.TokenAuthProvider
import com.basket.sample.scango.data.common.api.providers.TokenAuthProviderImpl
import com.basket.sample.scango.data.common.ktor.KtorClientFactory
import com.basket.sample.scango.data.common.ktor.config.ApiConfig
import com.basket.sample.scango.data.common.ktor.config.ApiLogLevel
import com.basket.sample.scango.data.common.ktor.config.ApiLogger
import com.basket.sample.scango.data.common.ktor.config.model.CustomHeaders
import com.basket.sample.scango.data.feature.authorization.token.repository.datasource.api.TokenApi
import com.basket.sample.scango.data.feature.authorization.token.repository.datasource.api.TokenApiImpl
import com.basket.sample.scango.presentation.core.platform.getPlatform
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val networkModule = module {

    single {
        ApiConfig(
            enableNetworkLogs = false,
            logger = ApiLogger.Simple,
            logLevel = ApiLogLevel.All,
            userAgent = "Scan&Go - ${getPlatform().name}",
            customHeaders = CustomHeaders(
                headers = mapOf(
                    "X-BASKET-SDK-CLIENT" to "Basket Scan&Go"
                )
            )
        )
    }

    singleOf(::TokenAuthProviderImpl) bind TokenAuthProvider::class

    singleOf(::BasketApiServerPathProviderImpl) bind ServerPathProvider::class

    factoryOf(::TokenApiImpl) bind TokenApi::class

    single {
        KtorClientFactory(
            tokenAuthProvider = get(),
            serverPathProvider = get(),
        ).build(apiConfig = get())
    }
}
