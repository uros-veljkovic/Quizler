package com.example.domain.usecase

import com.example.domain.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.concurrent.atomic.AtomicReference

interface IHandleStartupDataUseCase {
    operator fun invoke(): Flow<State<Float>>
}

class HandleStartupDataUseCase(
    private val useCases: List<IFetchAndCacheUseCase>,
) : IHandleStartupDataUseCase {

    private val progress: AtomicReference<Float> = AtomicReference(NO_PROGRESS)
    private val singleProgressIncrement: Float = 1f / useCases.count()

    override operator fun invoke(): Flow<State<Float>> = flow {
        resetProgress()

        useCases.forEach {
            val result = it.fetchAndCache(false)
            emitProgressIfSuccess(result)
        }
    }.flowOn(Dispatchers.IO)

    private fun resetProgress() {
        progress.set(NO_PROGRESS)
    }

    private suspend fun FlowCollector<State<Float>>.emitProgressIfSuccess(result: State<*>) {
        if (result is State.Success) {
            progress.set(progress.get() + (singleProgressIncrement))
            if (progress.get() < 1f) {
                emit(State.Loading(progress.get()))
            } else {
                emit(State.Success(progress.get()))
            }
        } else {
            resetProgress()
            emit(State.Error(result.error))
        }
    }

    companion object {
        const val NO_PROGRESS = 0.0f
    }
}
