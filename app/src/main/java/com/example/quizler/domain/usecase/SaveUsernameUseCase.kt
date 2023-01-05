package com.example.quizler.domain.usecase

import com.example.quizler.domain.preferences.IUserPreferences
import javax.inject.Inject

class SaveUsernameUseCase @Inject constructor(
    private val preferences: IUserPreferences
) {
    suspend operator fun invoke(username: String) {
        preferences.setUsername(username)
    }
}
