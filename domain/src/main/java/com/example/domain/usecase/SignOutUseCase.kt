package com.example.domain.usecase

import com.example.data.preferences.IAppPreferences
import com.example.data.preferences.IUserPreferences

interface ISignOutUseCase {
    suspend fun invoke()
}

class SignOutUseCase(
    private val appPreferences: IAppPreferences,
    private val userPreferences: IUserPreferences
) : ISignOutUseCase {
    override suspend fun invoke() {
        with(appPreferences) {
            clearToken()
            clearTokenProvider()
        }
        with(userPreferences) {
            clearUserId()
            clearUsername()
            clearAvatar()
            clearProfileImage()
        }
    }
}
