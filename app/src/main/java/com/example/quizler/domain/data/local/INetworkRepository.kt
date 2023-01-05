package com.example.quizler.domain.data.local

import kotlinx.coroutines.flow.StateFlow

interface INetworkRepository {

    suspend fun setHasInternetConnection(hasConnection: Boolean)
    fun getHasInternetConnectionFlow(): StateFlow<Boolean?>
    fun getHasInternetConnection() = getHasInternetConnectionFlow().value ?: false
}
