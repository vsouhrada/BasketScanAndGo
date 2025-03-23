package com.basket.sample.scango.data.common.ktor.config

import com.basket.sample.scango.data.common.ktor.config.model.CustomHeaders

/**
 * Configuration class for setting up API-related settings, including logging, user agent, content type,
 * and custom headers to be applied to all API requests.
 *
 * @property enableNetworkLogs A flag to enable or disable network logs.
 * @property logger The logger used for API logging, defaults to `ApiLogger.Default`.
 * @property logLevel The level of logging to be applied (e.g., Info, Debug).
 * @property userAgent A string representing the user agent to be sent with each request.
 * @property contentType The content type of requests, defaults to "application/json;Format=Plain".
 * @property customHeaders An instance of [CustomHeaders] containing key-value pairs that represent custom
 *                         headers to be applied to API requests. Each key is the header name, and each value
 *                         is the corresponding header value.
 *
 * Example usage:
 * ```
 * val apiConfig = ApiConfig(
 *     enableNetworkLogs = true,
 *     userAgent = "MyApp/1.0",
 *     customHeaders = CustomHeaders(
 *         headers = mapOf("Authorization" to "Bearer token_value")
 *     )
 * )
 * ```
 */
data class ApiConfig(
    val enableNetworkLogs: Boolean,
    val logger: ApiLogger = ApiLogger.Default,
    val logLevel: ApiLogLevel = ApiLogLevel.Info,
    val userAgent: String,
    val contentType: String = "application/json;Format=Plain",
    val customHeaders: CustomHeaders = CustomHeaders(),
) {
    /**
     * Returns a new instance of [ApiConfig] with an additional header.
     *
     * @param key The header key to add or replace.
     * @param value The value associated with the header key.
     * @return A new [ApiConfig] instance with the updated headers.
     * @since 1.6.0
     */
    fun withHeader(
        key: String,
        value: String,
    ): ApiConfig {
        customHeaders.withHeader(key, value)
        return this
    }

    /**
     * Removes the specified header from this [ApiConfig]'s custom headers.
     *
     * If the header key does not exist in [customHeaders], the [ApiConfig] instance remains unchanged.
     *
     * **Example Usage:**
     * ```kotlin
     * val apiConfig = get<ApiConfig>()
     * apiConfig.removeHeader("Authorization")
     * println(apiConfig.customHeaders.headers)  // The "Authorization" header is removed if it existed
     * ```
     *
     * @param key The name of the HTTP header to remove.
     * @return The same [ApiConfig] instance with the [customHeaders] updated to exclude the specified header.
     * @since 1.6.0
     */
    fun removeHeader(key: String): ApiConfig {
        customHeaders.removeHeader(key)
        return this
    }
}

sealed class ApiLogger {
    object Default : ApiLogger()

    object Simple : ApiLogger()
}

sealed class ApiLogLevel {
    object All : ApiLogLevel()

    object Headers : ApiLogLevel()

    object Body : ApiLogLevel()

    object Info : ApiLogLevel()

    object None : ApiLogLevel()
}
