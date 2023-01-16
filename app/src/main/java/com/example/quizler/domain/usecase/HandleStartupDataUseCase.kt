package com.example.quizler.domain.usecase

import com.example.quizler.util.State
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import java.util.concurrent.atomic.AtomicReference
import javax.inject.Inject

class HandleStartupDataUseCase @Inject constructor(
    private val useCases: List<IFetchAndCacheUseCase>,
) {

    private val progress: AtomicReference<Float> = AtomicReference(NO_PROGRESS)
    private val singleProgressIncrement: Float = 1f / useCases.count()

    operator fun invoke() = flow {
        resetProgress()

        useCases.forEach {
            val result = it.fetchAndCache()
            emitProgressIfSuccess(result)
        }
    }

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
            emit(
                State.Error(result.error)
            )
        }
    }

    companion object {
        const val NO_PROGRESS = 0.0f
    }
}
