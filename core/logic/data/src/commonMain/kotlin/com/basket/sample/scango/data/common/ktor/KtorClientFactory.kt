package com.basket.sample.scango.data.common.ktor

import com.basket.sample.scango.data.common.api.providers.ServerPathProvider
import com.basket.sample.scango.data.common.api.providers.TokenAuthProvider
import com.basket.sample.scango.data.common.ktor.config.ApiConfig
import com.basket.sample.scango.data.common.ktor.config.ApiLogLevel
import com.basket.sample.scango.data.common.ktor.config.ApiLogger
import com.basket.sample.scango.data.core.api.rest.exception.BasketRequestException
import com.basket.sample.scango.data.core.api.rest.exception.BasketRequestTimeoutException
import com.basket.sample.scango.data.core.api.rest.model.BasketRequestErrorData
import io.ktor.client.HttpClient
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.authProviders
import io.ktor.client.plugins.auth.providers.BearerAuthProvider
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.appendIfNameAbsent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule

open class KtorClientFactory(
    private val tokenAuthProvider: TokenAuthProvider,
    private val serverPathProvider: ServerPathProvider,
) {
    private var bearerTokenStorage: BearerTokens? = null

    fun build(apiConfig: ApiConfig) = HttpClient {
        expectSuccess = true

        HttpResponseValidator {
            handleResponseExceptionWithRequest { exception, _ ->
                when (exception) {
                    is ClientRequestException -> { // Handle 4xx errors
                        parseError(exception.response.bodyAsText())?.let {
                            throw BasketRequestException(
                                errorCode = exception.response.status.value,
                                errorData = it,
                            )
                        }
                    }

                    is ConnectTimeoutException,
                    is SocketTimeoutException,
                    is HttpRequestTimeoutException,
                    -> {
                        throw BasketRequestTimeoutException(exception)
                    }

                    else -> return@handleResponseExceptionWithRequest
                }
            }
        }

        defaultRequest {
            with(apiConfig) {
                val serverPathInfo = serverPathProvider.provideServerPath()
                url("${serverPathInfo.basePath}${serverPathInfo.contextPath}")
                headers.appendIfNameAbsent(HttpHeaders.Accept, "application/json;Format=Plain")
                headers.appendIfNameAbsent(HttpHeaders.Connection, "keep-alive")
                headers.appendIfNameAbsent(HttpHeaders.UserAgent, userAgent)
                headers.appendIfNameAbsent(HttpHeaders.ContentType, contentType)
                if (customHeaders.headers.isNotEmpty()) {
                    customHeaders.headers.forEach { entry ->
                        headers.appendIfNameAbsent(entry.key, entry.value)
                    }
                }
            }
        }

        install(Auth) {
            bearer {
                loadTokens {
                    if (bearerTokenStorage == null) {
                        tokenAuthProvider.getAccessToken().run {
                            bearerTokenStorage =
                                BearerTokens(accessToken = accessToken, refreshToken = refreshToken)
                        }
                    }
                    bearerTokenStorage
                }

                refreshTokens {
                    tokenAuthProvider.refreshToken().run {
                        bearerTokenStorage = BearerTokens(accessToken = accessToken, refreshToken = refreshToken)
                    }
                    bearerTokenStorage
                }
            }
        }

        install(ContentNegotiation) {
            json(jsonObject)
        }

        if (apiConfig.enableNetworkLogs) {
            install(Logging) {
                logger = getLoggerType(apiConfig.logger)
                level = getLoggerLevel(apiConfig.logLevel)
            }
        }

        install(HttpTimeout) {
            val timeout = 30000L
            connectTimeoutMillis = timeout
            requestTimeoutMillis = timeout
            socketTimeoutMillis = timeout
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    private val jsonObject =
        Json {
            ignoreUnknownKeys = true
            prettyPrint = true
            isLenient = true
            explicitNulls = false
            allowSpecialFloatingPointValues = true

            getSerializersModule()?.let { module ->
                serializersModule = module
            }
        }

    private fun parseError(bodyText: String): BasketRequestErrorData? {
        return try {
            jsonObject.decodeFromString<BasketRequestErrorData>(bodyText)
        } catch (ex: Exception) {
            null
        }
    }

    /**
     * Resets the stored authentication tokens and clears the token from the HTTP client.
     *
     * This method invalidates the current bearer token storage and removes any
     * cached tokens from the provided [HttpClient]'s `Auth` plugin.
     * It ensures that subsequent requests will not use the previously cached tokens.
     *
     * @param client The [HttpClient] instance from which the token will be cleared.
     * @since 2.0.0
     */
    fun resetTokens(client: HttpClient) {
        bearerTokenStorage = null
        client.authProviders.filterIsInstance<BearerAuthProvider>().firstOrNull()?.clearToken()
    }

    open fun getSerializersModule(): SerializersModule? {
        return null
    }

    private fun getLoggerLevel(logLevel: ApiLogLevel) = when (logLevel) {
        ApiLogLevel.All -> LogLevel.ALL
        ApiLogLevel.Body -> LogLevel.BODY
        ApiLogLevel.Headers -> LogLevel.HEADERS
        ApiLogLevel.Info -> LogLevel.INFO
        ApiLogLevel.None -> LogLevel.NONE
    }

    private fun getLoggerType(logger: ApiLogger) = when (logger) {
        ApiLogger.Default -> Logger.DEFAULT
        ApiLogger.Simple -> Logger.SIMPLE
    }
}
