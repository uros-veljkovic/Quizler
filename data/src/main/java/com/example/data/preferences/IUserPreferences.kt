package com.example.data.preferences

import kotlinx.coroutines.flow.Flow

interface IUserPreferences {

    /**
     * Sets new username to be saved
     *
     * @param value new username to be saved
     */
    suspend fun setUserId(value: String)

    /**
     * @return last saved username
     */
    fun getUserId(): Flow<String>

    /**
     * Sets new username to be saved
     *
     * @param value new username to be saved
     */
    suspend fun setUsername(value: String?)

    /**
     * @return last saved username
     */
    fun getUsername(): Flow<String>

    /**
     * Sets new username to be saved
     *
     * @param value new username to be saved
     */
    suspend fun setAvatar(value: String?)

    /**
     * @return last saved username
     */
    fun getAvatar(): Flow<String>

    /**
     * Sets users profile image URL
     *
     * @param value URL of the users profile image to be saved
     */
    suspend fun setProfileImageUrl(value: String?)

    /**
     * @return users profile image URL
     */
    fun getProfileImage(): Flow<String?>
}
