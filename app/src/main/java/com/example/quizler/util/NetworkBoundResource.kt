package com.example.quizler.util

import androidx.annotation.StringRes
import com.example.quizler.domain.data.RepositoryResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import timber.log.Timber

/**
 * It starts by observing the database for the resource. When the entry is loaded from the database
 * for the first time, NetworkBoundResource checks whether the result is good enough to be dispatched
 * or that it should be re-fetched from the network. Note that both of these situations can happen
 * at the same time, given that you probably want to show cached data while updating it from the network.
 *
 * If the network call completes successfully, it saves the response into the database
 * and re-initializes the stream. If network request fails, the NetworkBoundResource
 * dispatches a failure directly.
 */
inline fun <ResultType, RequestType> networkBoundResource(
    @StringRes errorMessageId: Int? = null,
    crossinline query: () -> Flow<ResultType>,
    crossinline fetchData: suspend () -> RepositoryResponse<RequestType>,
    crossinline cache: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true },
) = flow {

    val data = query().first()

    val flow = if (shouldFetch(data)) {
        emit(State.Loading(data))

        try {
            when (val fetchResult = fetchData()) {
                is RepositoryResponse.Failure -> State.Error(
                    throwable = fetchResult.throwable,
                    data = data
                )
                is RepositoryResponse.Success -> cache(fetchResult.data)
            }
            query().map { State.Success(it) }
        } catch (throwable: Throwable) {
            Timber.d(throwable)
            query().map { State.Error(throwable, it, errorMessageId) }
        }
    } else {
        query().map { State.Success(it) }
    }

    emitAll(flow)
}
