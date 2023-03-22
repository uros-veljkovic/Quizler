package com.example.domain.network.inspector

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.util.network.inspector.INetworkInspector

class NetworkInspector(private val context: Context) : INetworkInspector {

    companion object {
        const val WIFI_ENABLED = "WIFI enabled"
        const val MOBILE_DATA_ENABLED = "Mobile Data Enabled"
        const val NO_INTERNET_CONNECTION = "No Internet Connection"
    }

    private var connectionType: String? = null

    override val isConnected: Boolean
        get() {
            processCurrentConnection()
            return getConnectionType() != NO_INTERNET_CONNECTION
        }

    override val isConnectedWifi: Boolean
        get() = getConnectionType() == WIFI_ENABLED

    override val isConnectedMobile: Boolean
        get() = getConnectionType() == MOBILE_DATA_ENABLED

    private fun processCurrentConnection() {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val connectionStatus = getConnectionStatus(cm)
        connectionType = connectionStatus
    }

    private fun getConnectionStatus(cm: ConnectivityManager): String {
        val connectionStatus: String
        val network = cm.activeNetwork
        connectionStatus = if (network != null) {
            val networkCapabilities = cm.getNetworkCapabilities(network)
            if (networkCapabilities != null) {
                when {
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> WIFI_ENABLED
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> MOBILE_DATA_ENABLED
                    else -> NO_INTERNET_CONNECTION
                }
            } else {
                NO_INTERNET_CONNECTION
            }
        } else {
            NO_INTERNET_CONNECTION
        }
        return connectionStatus
    }

    private fun getConnectionType(): String? {
        if (connectionType == null) processCurrentConnection()
        return connectionType
    }
}
