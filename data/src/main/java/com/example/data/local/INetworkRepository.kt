package com.example.data.local

import kotlinx.coroutines.flow.StateFlow

interface INetworkRepository {
    suspend fun setHasInternetConnection(hasConnection: Boolean)
    fun getHasInternetConnectionFlow(): StateFlow<Boolean?>
}
