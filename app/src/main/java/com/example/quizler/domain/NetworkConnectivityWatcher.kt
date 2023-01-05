package com.example.quizler.domain

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import com.example.quizler.domain.data.local.INetworkRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class NetworkConnectivityWatcher @Inject constructor(
    private val repository: INetworkRepository,
) {

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {

        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            super.onCapabilitiesChanged(network, networkCapabilities)

            val hasConnection = networkCapabilities.hasCapability(
                NetworkCapabilities.NET_CAPABILITY_INTERNET
            )
            setHasConnection(hasConnection)
        }

        override fun onUnavailable() {
            super.onUnavailable()
            setHasConnection(NO_CONNECTION)
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            setHasConnection(NO_CONNECTION)
        }
    }

    fun observeConnectivity(context: Context) {
        val networkRequest =
            NetworkRequest.Builder().addCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR).build()
        val connectivityManager =
            context.getSystemService(ConnectivityManager::class.java) as ConnectivityManager
        connectivityManager.requestNetwork(networkRequest, networkCallback)
    }

    private fun setHasConnection(hasConnection: Boolean) {
        CoroutineScope(Dispatchers.Default).launch {
            repository.setHasInternetConnection(hasConnection)
            Log.d(TAG, "onCapabilitiesChanged: $hasConnection")
        }
    }

    companion object {
        const val NO_CONNECTION = false
        const val TAG = "NetworkConnectivityWatcher"
    }
}
