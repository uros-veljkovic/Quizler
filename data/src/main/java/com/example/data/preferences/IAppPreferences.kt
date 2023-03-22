package com.example.data.preferences

import kotlinx.coroutines.flow.Flow

interface IAppPreferences : IDataSyncCoordinator

interface IDataSyncCoordinator {
    /**
     * Sets time of last data syncronisation to local storage
     *
     * @param timeInMillis time when all the data was syncrhonised for the last time
     */
    suspend fun setDataSyncTime(timeInMillis: Long)

    /**
     * Gets time in millis of when app data was synchronised for the last time
     *
     * @return time in millis
     */
    fun getDataSyncTime(): Flow<Long>
}
