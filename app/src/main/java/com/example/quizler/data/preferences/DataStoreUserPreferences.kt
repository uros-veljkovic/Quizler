package com.example.quizler.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.quizler.domain.preferences.IUserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreUserPreferences(
    private val preferences: DataStore<Preferences>
) : IUserPreferences {
    override suspend fun setIsOnboarded(value: Boolean) {
        preferences.edit {
            it[KEY_IS_ONBOARDED] = value
        }
    }

    override suspend fun setUsername(value: String) {
        preferences.edit {
            it[KEY_USERNAME] = value
        }
    }

    override fun getIsOnboarded(): Flow<Boolean> {
        return preferences.data.map { it[KEY_IS_ONBOARDED] ?: NOT_ONBOARDED }
    }

    override fun getUsername(): Flow<String> {
        return preferences.data.map { it[KEY_USERNAME] ?: NO_USERNAME }
    }

    private companion object {
        val KEY_USERNAME = stringPreferencesKey("username")
        val KEY_IS_ONBOARDED = booleanPreferencesKey("isOnboarded")

        const val NO_USERNAME = ""
        const val NOT_ONBOARDED = false
    }
}
