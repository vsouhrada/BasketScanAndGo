package com.basket.sample.scango.data.common.ktor.config.model

/**
 * Represents custom HTTP headers to be included in API requests.
 *
 * This class encapsulates a map of HTTP header names and their corresponding values.
 * It provides immutability and allows for easy addition of new headers through the [withHeader] method.
 *
 * @property headers A map containing key-value pairs of HTTP headers.
 *  * By default, it is an empty map, but headers can be added or modified as needed.
 * @since 1.5.0
 *
 * Example usage:
 * ```
 * // Create an instance with no initial headers
 * val customHeaders = CustomHeaders()
 *
 * // Add an Authorization header
 * customHeaders.withHeader("Authorization", "Bearer token123")
 *
 * // Add more headers
 * customHeaders
 *     .withHeader("X-App-Version", "1.0.0")
 *     .withHeader("Content-Type", "application/json")
 *
 * // Accessing headers
 * println(finalHeaders.headers["Authorization"])  // Output: Bearer token123
 * println(finalHeaders.headers["X-App-Version"])  // Output: 1.0.0
 * ```
 */
data class CustomHeaders(var headers: Map<String, String> = emptyMap()) {
    /**
     * Creates a new copy of the current [CustomHeaders] object with the specified header added or updated.
     * If the header already exists in the map, its value is replaced by the new one provided.
     *
     * This function does not modify the original headers map; instead, it returns a new map with the changes.
     *
     * Example usage:
     * ```
     * val customHeaders = CustomHeaders()
     * val updatedHeaders = customHeaders.withHeader("Authorization", "Bearer token123")
     * ```
     *
     * @param key The name of the HTTP header to add or update.
     * @param value The value of the HTTP header.
     * @return A new instance of [CustomHeaders] with the updated headers map.
     */
    fun withHeader(
        key: String,
        value: String,
    ): CustomHeaders {
        headers = headers + (key to value)
        return this
    }

    /**
     * Returns a new instance of [CustomHeaders] with the specified header removed.
     * If the specified header key does not exist, the headers remain unchanged.
     *
     * @param key The name of the HTTP header to remove.
     * @return A new [CustomHeaders] instance without the specified header.
     * @since 1.6.0
     */
    fun removeHeader(key: String): CustomHeaders {
        headers = headers - key
        return this
    }
}
