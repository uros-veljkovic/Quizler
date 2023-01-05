package com.example.quizler.data.local

import com.example.quizler.domain.data.local.INetworkRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class NetworkRepository @Inject constructor() : INetworkRepository {

    private val hasInternetConnection = MutableStateFlow<Boolean?>(true)

    override suspend fun setHasInternetConnection(hasConnection: Boolean) {
        hasInternetConnection.value = hasConnection
    }

    override fun getHasInternetConnectionFlow(): StateFlow<Boolean?> {
        return hasInternetConnection.asStateFlow()
    }
}
