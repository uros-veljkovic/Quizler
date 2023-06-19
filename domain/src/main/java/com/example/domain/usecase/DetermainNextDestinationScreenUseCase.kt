package com.example.domain.usecase

import com.example.data.preferences.IAppPreferences
import com.example.data.preferences.IUserPreferences
import com.example.domain.model.FirstDestination
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull

/**
 * Determains the first screen destination on fresh start.
 */
interface IDetermainNextDestinationScreenUseCase {
    suspend operator fun invoke(): FirstDestination
}

class DetermainNextDestinationScreenUseCase(
    private val appPreferences: IAppPreferences,
    private val userPreferences: IUserPreferences,
) : IDetermainNextDestinationScreenUseCase {
    override suspend fun invoke(): FirstDestination {
        return when {
            appPreferences.getToken().firstOrNull().isNullOrEmpty() -> FirstDestination.SignIn
            isUserFullyCreated().not() -> FirstDestination.CreateProfile
            else -> FirstDestination.Splash
        }
    }

    private suspend fun isUserFullyCreated(): Boolean {
        return userPreferences.getAvatar().first().isNotEmpty() &&
            userPreferences.getUsername().first().isNotEmpty()
    }
}
