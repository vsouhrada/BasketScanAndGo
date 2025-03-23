package com.basket.sample.scango.data.core.api.rest

import io.ktor.client.HttpClient

abstract class BaseApi(
    protected open val client: HttpClient,
)
