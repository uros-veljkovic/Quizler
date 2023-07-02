package com.example.data.preferences

import kotlinx.coroutines.flow.Flow

interface IUserPreferences {

    /**
     * @return Last saved user ID
     */
    fun getUserId(): Flow<String>

    /**
     * @return Last saved username
     */
    fun getUsername(): Flow<String>

    /**
     * @return Last saved avatar URL
     */
    fun getAvatar(): Flow<String>

    /**
     * @return Last saved user's profile image URL. If no URL was saved, returns null.
     */
    fun getProfileImage(): Flow<String?>

    /**
     * Sets new user ID to be saved
     *
     * @param value New user ID to be saved
     */
    suspend fun setUserId(value: String)

    /**
     * Sets new username to be saved
     *
     * @param value New username to be saved. If null, the username will be cleared.
     */
    suspend fun setUsername(value: String?)

    /**
     * Sets new avatar URL to be saved
     *
     * @param value New avatar URL to be saved. If null, the avatar will be cleared.
     */
    suspend fun setAvatar(value: String?)

    /**
     * Sets new user's profile image URL to be saved
     *
     * @param value New URL of the user's profile image to be saved. If null, the profile image URL will be cleared.
     */
    suspend fun setProfileImageUrl(value: String?)

    /**
     * Clears the saved user ID
     */
    suspend fun clearUserId()

    /**
     * Clears the saved username
     */
    suspend fun clearUsername()

    /**
     * Clears the saved avatar URL
     */
    suspend fun clearAvatar()

    /**
     * Clears the saved user's profile image URL
     */
    suspend fun clearProfileImage()
}
