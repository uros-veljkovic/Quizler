package com.example.quizler.util

import com.example.quizler.R
import com.example.quizler.domain.data.RepositoryResponse
import com.example.quizler.domain.data.local.INetworkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import retrofit2.Response
import timber.log.Timber
import java.net.ConnectException
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ServerUnavailableException : Exception("Unsuccessful response")
class ConnectivityException : Exception("No connection")

class NetworkActionHandler @Inject constructor(
    private val coroutineContext: CoroutineContext,
    private val networkRepository: INetworkRepository
) : INetworkActionHandler {
    override suspend fun <T> executeNetworkAction(load: suspend () -> Response<T>): RepositoryResponse<T> {
        return withContext(coroutineContext) {
            try {
                if (networkRepository.getHasInternetConnection().not()) {
                    return@withContext RepositoryResponse.Failure(ConnectivityException())
                }
                load().let { response ->
                    if (response.isSuccessful) {
                        RepositoryResponse.Success(response.body()!!)
                    } else {
                        RepositoryResponse.Failure(Throwable(response.errorBody()?.string()))
                    }
                }
            } catch (e: ConnectException) {
                Timber.e(e)
                RepositoryResponse.Failure(ServerUnavailableException())
            } catch (e: Exception) {
                Timber.e(e)
                RepositoryResponse.Failure(ServerUnavailableException())
            }
        }
    }

    override suspend fun <Entity, Dto> fetchAndCache(
        shouldFetch: (Entity) -> Boolean,
        query: () -> Flow<Entity>,
        fetch: suspend () -> RepositoryResponse<Dto>,
        cache: suspend (Dto) -> Unit
    ): State<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                val data = query().first()

                if (shouldFetch(data)) {
                    if (networkRepository.getHasInternetConnection().not()) {
                        // FIXME: DataManagementService to update on connection change
                        return@withContext State.Error(ConnectivityException())
                    }
                    when (val fetchResult = fetch()) {
                        is RepositoryResponse.Failure -> {
                            Timber.e(fetchResult.throwable)
                            State.Error(ServerUnavailableException())
                        }

                        is RepositoryResponse.Success -> {
                            cache(fetchResult.data)
                            State.Success(Unit)
                        }
                    }
                } else {
                    State.Success(Unit)
                }
            } catch (e: ConnectException) {
                Timber.e(e)
                State.Error(
                    throwable = ServerUnavailableException(),
                    messageTitleResId = R.string.no_internet_connection,
                    messageDescriptionResId = R.string.no_internet_connection_description
                )
            } catch (e: Exception) {
                Timber.e(e)
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
    ): State<Unit>
}
