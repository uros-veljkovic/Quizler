package com.example.domain.network

import com.example.domain.State
import com.example.util.data.RepositoryResponse
import com.example.util.exceptions.ConnectivityException
import com.example.util.exceptions.ServerUnavailableException
import com.example.util.logger.ILogger
import com.example.util.network.inspector.INetworkInspector
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.net.ConnectException
import kotlin.coroutines.CoroutineContext

class FetchAndCacheManager(
    private val coroutineContext: CoroutineContext,
    private val networkInspector: INetworkInspector,
    private val logger: ILogger
) : IFetchAndCacheManager {
    override suspend fun <Entity, Dto> invoke(
        shouldFetch: (Entity) -> Boolean,
        query: () -> Flow<Entity>,
        fetch: suspend () -> RepositoryResponse<Dto>,
        cache: suspend (Dto) -> Unit
    ): State<Unit> {
        return withContext(coroutineContext) {
            try {
                val data = query().first()

                if (shouldFetch(data)) {
                    when (val fetchResult = fetch()) {
                        is RepositoryResponse.Failure -> {
                            logger.error(fetchResult.throwable)
                            State.Error(getException())
                        }

                        is RepositoryResponse.Success -> {
                            logger.info("Great: ${fetchResult::class.java}")
                            cache(fetchResult.data)
                            State.Success(Unit)
                        }
                    }
                } else {
                    State.Success(Unit)
                }
            } catch (e: ConnectException) {
                logger.error(e)
                State.Error(throwable = getException())
            } catch (e: Exception) {
                logger.error(e)
                State.Error(throwable = getException())
            }
        }
    }

    private fun getException() = if (networkInspector.isConnected) ServerUnavailableException()
    else ConnectivityException()
}
