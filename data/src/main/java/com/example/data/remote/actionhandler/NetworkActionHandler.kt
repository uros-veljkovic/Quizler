package com.example.data.remote.actionhandler

import com.example.util.data.RepositoryResponse
import com.example.util.exceptions.ConnectivityException
import com.example.util.exceptions.ServerUnavailableException
import com.example.util.logger.ILogger
import com.example.util.network.inspector.INetworkInspector
import kotlinx.coroutines.withContext
import retrofit2.Response
import kotlin.coroutines.CoroutineContext

class NetworkActionHandler(
    private val coroutineContext: CoroutineContext,
    private val networkInspector: INetworkInspector,
    private val logger: ILogger
) : INetworkActionHandler {
    override suspend fun <T> invoke(load: suspend () -> Response<T>): RepositoryResponse<T> {
        return withContext(coroutineContext) {
            try {
                load().let { response ->
                    if (response.isSuccessful) {
                        RepositoryResponse.Success(response.body()!!)
                    } else {
                        logger.error(response.errorBody().toString())
                        RepositoryResponse.Failure(getAppropriateException())
                    }
                }
            } catch (e: Exception) {
                logger.error(e)
                RepositoryResponse.Failure(getAppropriateException())
            }
        }
    }

    private fun getAppropriateException() = if (networkInspector.isConnected.not())
        ConnectivityException()
    else
        ServerUnavailableException()
}
