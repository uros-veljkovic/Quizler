package com.example.quizler.util

import com.example.quizler.R
import com.example.quizler.domain.data.RepositoryResponse
import com.example.quizler.domain.data.local.INetworkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.net.ConnectException
import javax.inject.Inject

class ServerUnavailableException : Exception("Unsuccessful response")
class ConnectivityException : Exception("No connection")

class NetworkActionHandler @Inject constructor(
    private val networkRepository: INetworkRepository
) : INetworkActionHandler {
    override suspend fun <T> executeNetworkAction(load: suspend () -> Response<T>): RepositoryResponse<T> {
        return withContext(Dispatchers.IO) {
            try {
                if (networkRepository.getHasInternetConnection().not()) {
                    return@withContext RepositoryResponse.Failure(ConnectivityException())
                }
                load().let { response ->
                    if (response.isSuccessful) {
                        RepositoryResponse.Success(response.body()!!)
                    } else {
                        RepositoryResponse.Failure(Throwable(response.message()))
                    }
                }
            } catch (e: ConnectException) {
                RepositoryResponse.Failure(ServerUnavailableException())
            } catch (e: Exception) {
                RepositoryResponse.Failure(ServerUnavailableException())
            }
        }
    }

    override suspend fun <Entity, Dto> fetchAndCache(
        shouldFetch: (Entity) -> Boolean,
        query: () -> Flow<Entity>,
        fetch: suspend () -> RepositoryResponse<Dto>,
        cache: suspend (Dto) -> Unit
    ): State<Entity> {
        return withContext(Dispatchers.IO) {
            try {
                val data = query().first()

                if (shouldFetch(data)) {
                    if (networkRepository.getHasInternetConnection().not()) {
                        // FIXME: DataManagementService to update on connection change
                        return@withContext State.Error(ConnectivityException())
                    }
                    when (val fetchResult = fetch()) {
                        is RepositoryResponse.Failure -> State.Error(ServerUnavailableException())
                        is RepositoryResponse.Success -> {
                            cache(fetchResult.data)
                            val cachedData = query().first()
                            State.Success(cachedData)
                        }
                    }
                } else {
                    State.Success(data)
                }
            } catch (e: ConnectException) {
                State.Error(
                    throwable = ServerUnavailableException(),
                    messageTitleResId = R.string.no_internet_connection,
                    messageDescriptionResId = R.string.no_internet_connection_description
                )
            } catch (e: Exception) {
                State.Error(
                    throwable = e,
                    messageTitleResId = R.string.error_unexpected,
                    messageDescriptionResId = R.string.error_unexpected_description
                )
            }
        }
    }
}

interface INetworkActionHandler {
    suspend fun <T> executeNetworkAction(load: suspend () -> Response<T>): RepositoryResponse<T>
    suspend fun <Entity, Dto> fetchAndCache(
        shouldFetch: (Entity) -> Boolean,
        query: () -> Flow<Entity>,
        fetch: suspend () -> RepositoryResponse<Dto>,
        cache: suspend (Dto) -> Unit
    ): State<Entity>
}
