package com.basket.sample.scango.data.common.api.providers

import com.basket.sample.scango.domain.feature.authorization.model.TokenInfo

/**
 * Interface defining the contract for handling token-based authentication.
 *
 * Implementations of this interface are responsible for providing access tokens
 * and refreshing them as needed to maintain authenticated access to resources.
 * @since 0.1.0
 */
interface TokenAuthProvider {
    /**
     * Retrieves the current access token.
     *
     * This method is used to obtain a valid access token for authentication.
     * It is typically called when an access token is required for making
     * authenticated requests.
     *
     * @return [com.basket.sample.scango.data.common.api.providers.model.TokenInfo] containing the access token details.
     * @throws Exception if retrieving the token fails.
     * @since 1.2.0
     */
    suspend fun getAccessToken(): TokenInfo

    /**
     * Refreshes the authentication token.
     *
     * This method generates a new access token, replacing the expired or invalid
     * token. It ensures continuity of authenticated access to resources.
     *
     * @return [TokenInfo] containing the refreshed token details.
     * @throws Exception if the token refresh process fails.
     * @since 0.1.0
     */
    suspend fun refreshToken(): TokenInfo
}
