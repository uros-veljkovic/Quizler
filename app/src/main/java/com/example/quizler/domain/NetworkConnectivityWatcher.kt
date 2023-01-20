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

    private lateinit var connectivityManager: ConnectivityManager
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
        checkFirstTimeNetworkAvailability(context)
        val networkRequest =
            NetworkRequest.Builder().addCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR).build()
        connectivityManager.requestNetwork(networkRequest, networkCallback)
    }

    private fun checkFirstTimeNetworkAvailability(context: Context) {
        connectivityManager = context.getSystemService(ConnectivityManager::class.java) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

        CoroutineScope(Dispatchers.Default).launch {
            setHasConnection(
                capabilities?.let {
                    it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || capabilities.hasTransport(
                        NetworkCapabilities.TRANSPORT_WIFI
                    )
                } ?: false
            )
        }
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
