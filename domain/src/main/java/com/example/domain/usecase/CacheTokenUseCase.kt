package com.example.domain.usecase

import com.example.data.preferences.IAppPreferences
import kotlinx.coroutines.flow.first

typealias IsCachedToken = Boolean

interface ICacheTokenUseCase {
    suspend operator fun invoke(token: String): IsCachedToken
}

class CacheTokenUseCase(
    private val preferences: IAppPreferences
) : ICacheTokenUseCase {
    override suspend fun invoke(token: String): IsCachedToken {
        return try {
            preferences.setToken(token)
            val cachedToken = preferences.getToken().first()

            cachedToken != null && cachedToken == token
        } catch (e: Exception) {
            false
        }
    }
}
