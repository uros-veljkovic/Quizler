package com.example.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserDataStorePreferences(
    private val preferences: DataStore<Preferences>
) : IUserPreferences {
    override suspend fun setUserId(value: String) {
        putStringPreference(KEY_USER_ID, value)
    }

    override suspend fun setUsername(value: String?) {
        putStringPreference(KEY_USERNAME, value ?: NO_VALUE)
    }

    override suspend fun setAvatar(value: String?) {
        putStringPreference(KEY_USER_AVATAR, value ?: NO_VALUE)
    }

    override suspend fun setProfileImageUrl(value: String?) {
        putStringPreference(KEY_USER_PROFILE_IMAGE_URL, value ?: NO_VALUE)
    }

    override suspend fun clearUserId() {
        setUserId(NO_VALUE)
    }

    override suspend fun clearUsername() {
        setUsername(NO_VALUE)
    }

    override suspend fun clearAvatar() {
        setAvatar(NO_VALUE)
    }

    override suspend fun clearProfileImage() {
        setProfileImageUrl(NO_VALUE)
    }

    override fun getUserId(): Flow<String> {
        return getStringPreference(KEY_USER_ID)
    }

    override fun getUsername(): Flow<String> {
        return getStringPreference(KEY_USERNAME)
    }

    override fun getAvatar(): Flow<String> {
        return getStringPreference(KEY_USER_AVATAR)
    }

    override fun getProfileImage(): Flow<String?> {
        return getStringPreference(KEY_USER_PROFILE_IMAGE_URL)
    }

    private fun getStringPreference(preferenceId: Preferences.Key<String>): Flow<String> {
        return preferences.data.map { it[preferenceId] ?: NO_VALUE }
    }

    private suspend fun putStringPreference(preferenceId: Preferences.Key<String>, value: String): Preferences {
        return preferences.edit { it[preferenceId] = value }
    }

    private companion object {
        val KEY_USER_ID = stringPreferencesKey("user_id")
        val KEY_USERNAME = stringPreferencesKey("username")
        val KEY_USER_AVATAR = stringPreferencesKey("user_avatar")
        val KEY_USER_PROFILE_IMAGE_URL = stringPreferencesKey("user_profile_image_url")

        const val NO_VALUE = ""
    }
}
