package com.example.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Date

class AppDataStorePreferences(
    private val preferences: DataStore<Preferences>
) : IAppPreferences {
    override suspend fun setDataSyncTime(timeInMillis: Long) {
        preferences.edit {
            it[KEY_LAST_SYNC] = timeInMillis
        }
    }

    override fun getDataSyncTime(): Flow<Long> {
        return preferences.data.map { it[KEY_LAST_SYNC] ?: Date().time }
    }

    companion object {
        val KEY_LAST_SYNC = longPreferencesKey("last_sync")
    }
}
