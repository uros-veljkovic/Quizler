package com.example.quizler.domain.usecase

import com.example.quizler.domain.preferences.IUserPreferences
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUsernameUseCase @Inject constructor(
    private val preferences: IUserPreferences
) {
    operator fun invoke(): Flow<String> {
        return preferences.getUsername()
    }
}
