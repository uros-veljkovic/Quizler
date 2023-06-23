package com.example.data.preferences

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking

interface IAppPreferences : IDataSyncCoordinator

interface IDataSyncCoordinator {

    /**
     * Caches a auth token
     *
     * @param token token claimed from sign in client
     */
    suspend fun setToken(token: String)

    /**
     * Removes token
     */
    suspend fun clearToken()

    /**
     * Retruns a auth token
     *
     * @return auth token, null if token does not exist
     */
    fun getTokenProvider(): Flow<String?>

    /**
     * Caches a auth token provider
     *
     * @param tokenProvider token claimed from sign in client
     */
    suspend fun setTokenProvider(tokenProvider: String)

    /**
     * Removes token provider
     */
    suspend fun clearTokenProvider()

    /**
     * Retruns a auth token provider
     *
     * @return auth token provider, null if token provider does not exist
     */
    fun getToken(): Flow<String?>

    /**
     * Retruns a auth token synchronously
     *
     * @return auth token, null if token does not exist
     */
    fun getTokenSynchronously(): String? = runBlocking { getToken().firstOrNull() }

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
