package com.example.domain.usecase

import com.example.data.preferences.IUserPreferences

interface ISaveUsernameUseCase {
    suspend operator fun invoke(username: String)
}

class SaveUsernameUseCase(
    private val preferences: IUserPreferences
) : ISaveUsernameUseCase {
    override suspend operator fun invoke(username: String) {
        preferences.setUsername(username)
    }
}
