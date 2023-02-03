package com.example.quizler.domain

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.example.quizler.domain.data.local.INetworkRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class NetworkConnectivityWatcher @Inject constructor(
    private val repository: INetworkRepository,
) {

    private lateinit var connectivityManager: ConnectivityManager
    private val networkCallback = object : ConnectivityManager.NetworkCallback() {

        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            setHasConnection(true)
        }

        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            super.onCapabilitiesChanged(network, networkCapabilities)
            setHasConnection(hasAnyTypeOfInternetConnection(networkCapabilities))
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            setHasConnection(NO_CONNECTION)
        }
    }

    fun observeConnectivity(context: Context) {
        checkFirstTimeNetworkAvailability(context)
        val networkRequest =
            NetworkRequest.Builder().addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR).build()
        connectivityManager.requestNetwork(networkRequest, networkCallback)
    }

    private fun checkFirstTimeNetworkAvailability(context: Context) {
        connectivityManager = context.getSystemService(ConnectivityManager::class.java) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

        setHasConnection(hasAnyTypeOfInternetConnection(capabilities))
    }

    private fun hasAnyTypeOfInternetConnection(capabilities: NetworkCapabilities?) =
        capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: NO_CONNECTION

    private fun setHasConnection(hasConnection: Boolean) {
        CoroutineScope(Dispatchers.Default).launch {
            repository.setHasInternetConnection(hasConnection)
            Timber.d(TAG, "onCapabilitiesChanged: $hasConnection")
        }
    }

    companion object {
        const val NO_CONNECTION = false
        const val TAG = "NetworkConnectivityWatcher"
    }
}
