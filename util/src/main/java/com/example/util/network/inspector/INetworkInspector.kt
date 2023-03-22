package com.example.util.network.inspector

interface INetworkInspector {
    val isConnected: Boolean
    val isConnectedWifi: Boolean
    val isConnectedMobile: Boolean
}
