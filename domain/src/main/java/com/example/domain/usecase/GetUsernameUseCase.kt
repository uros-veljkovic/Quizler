package com.example.domain.usecase

import com.example.data.preferences.IUserPreferences
import kotlinx.coroutines.flow.Flow

interface IGetUsernameUseCase {
    operator fun invoke(): Flow<String>
}

class GetUsernameUseCase(
    private val preferences: IUserPreferences
) : IGetUsernameUseCase {
    override operator fun invoke(): Flow<String> {
        return preferences.getUsername()
    }
}
