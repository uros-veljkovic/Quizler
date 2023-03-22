package com.example.util.exceptions

/**
 * Thrown when server is not avaialble
 */
class ServerUnavailableException : Exception("Unsuccessful response")

/**
 * Thrown when no connectivity is available
 */
class ConnectivityException : Exception("No connection")
