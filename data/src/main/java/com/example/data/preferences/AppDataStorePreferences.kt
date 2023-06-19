package com.example.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Date

class AppDataStorePreferences(
    private val preferences: DataStore<Preferences>
) : IAppPreferences {
    override suspend fun setToken(token: String) {
        putStringPreference(KEY_TOKEN, token)
    }

    override suspend fun clearToken() {
        putStringPreference(KEY_TOKEN, NO_VALUE)
    }

    override fun getToken(): Flow<String?> {
        return getStringPreference(KEY_TOKEN)
    }

    override suspend fun setDataSyncTime(timeInMillis: Long) {
        preferences.edit {
            it[KEY_LAST_SYNC] = timeInMillis
        }
    }

    override fun getDataSyncTime(): Flow<Long> {
        return preferences.data.map { it[KEY_LAST_SYNC] ?: Date().time }
    }

    private fun getStringPreference(preferenceId: Preferences.Key<String>): Flow<String> {
        return preferences.data.map { it[preferenceId] ?: NO_VALUE }
    }

    private suspend fun putStringPreference(preferenceId: Preferences.Key<String>, value: String): Preferences {
        return preferences.edit { it[preferenceId] = value }
    }

    companion object {
        val KEY_LAST_SYNC = longPreferencesKey("last_sync")
        val KEY_TOKEN = stringPreferencesKey("last_sync")

        const val NO_VALUE = ""
    }
}
