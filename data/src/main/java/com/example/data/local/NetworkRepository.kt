package com.example.data.local

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class NetworkRepository : INetworkRepository {

    private val hasInternetConnection = MutableStateFlow<Boolean?>(true)

    override suspend fun setHasInternetConnection(hasConnection: Boolean) {
        hasInternetConnection.value = hasConnection
    }

    override fun getHasInternetConnectionFlow(): StateFlow<Boolean?> {
        return hasInternetConnection.asStateFlow()
    }
}
