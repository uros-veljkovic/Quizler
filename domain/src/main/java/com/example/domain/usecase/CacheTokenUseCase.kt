package com.example.domain.usecase

import com.example.data.preferences.IAppPreferences
import kotlinx.coroutines.flow.first

typealias IsCachedToken = Boolean

interface ICacheTokenUseCase {
    /**
     * Caches token and its provider.
     *
     * @param token token to be cached
     * @param tokenProvider token provider to be cached (Google or Facebook)
     * @return
     */
    suspend operator fun invoke(token: String, tokenProvider: String): IsCachedToken
}

class CacheTokenUseCase(
    private val preferences: IAppPreferences
) : ICacheTokenUseCase {
    override suspend fun invoke(token: String, tokenProvider: String): IsCachedToken {
        return try {
            preferences.setToken(token)
            preferences.setTokenProvider(tokenProvider)
            val cachedToken = preferences.getToken().first()
            val cachedTokenProvider = preferences.getTokenProvider().first()

            cachedToken != null &&
                cachedTokenProvider != null &&
                cachedToken == token &&
                cachedTokenProvider == tokenProvider
        } catch (e: Exception) {
            false
        }
    }
}
