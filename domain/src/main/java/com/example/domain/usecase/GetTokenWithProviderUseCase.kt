package com.example.domain.usecase

import com.example.data.preferences.IAppPreferences
import kotlinx.coroutines.flow.first

data class TokenBundle(
    val token: String?,
    val tokenProvider: String?
)

interface IGetTokenWithProviderUseCase {
    suspend operator fun invoke(): TokenBundle
}

class GetTokenWithProviderUseCase(
    private val preferences: IAppPreferences
) : IGetTokenWithProviderUseCase {
    override suspend fun invoke(): TokenBundle {
        return TokenBundle(preferences.getToken().first(), preferences.getTokenProvider().first())
    }
}
