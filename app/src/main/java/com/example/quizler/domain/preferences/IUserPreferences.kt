package com.example.quizler.domain.preferences

import kotlinx.coroutines.flow.Flow

interface IUserPreferences {
    /**
     * Sets [Boolean] value that shows if a user is onboarded or not
     *
     * @param value indicator if a user is onboarded or not
     */
    suspend fun setIsOnboarded(value: Boolean = true)

    /**
     * Sets new username to be saved
     *
     * @param value new username to be saved
     */
    suspend fun setUsername(value: String)

    /**
     * Returns [Boolean] value that represents weather a user is onboarded or not
     *
     * @return true if onboarded, otherwise false
     */
    fun getIsOnboarded(): Flow<Boolean>

    /**
     * Returns last saved username
     *
     * @return
     */
    fun getUsername(): Flow<String>
}
