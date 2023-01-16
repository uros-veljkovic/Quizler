package com.example.quizler.domain.usecase

import com.example.quizler.util.State
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HandleStartupDataUseCase @Inject constructor(
    private val getQuestionsUseCase: GetQuestionsUseCase,
    private val getModesDifficultyUseCase: GetModesDifficultyUseCase,
    private val getModesCategoryUseCase: GetModesCategoryUseCase,
    private val getModesLengthUseCase: GetModesLengthUseCase,
    private val getScoresUseCase: GetScoresUseCase,
    private val getReportTypesUseCase: GetReportTypesUseCase,
) {

    private var progress = State.Success(0.0f)

    operator fun invoke() = flow {
        resetProgress()
        val result1 = getQuestionsUseCase.fetchAndCacheData()
        emitProgressIfSuccess(result1)
        val result2 = getModesDifficultyUseCase.fetchAndCacheData()
        emitProgressIfSuccess(result2)
        val result3 = getModesLengthUseCase.fetchAndCacheData()
        emitProgressIfSuccess(result3)
        val result4 = getModesCategoryUseCase.fetchAndCacheData()
        emitProgressIfSuccess(result4)
        val result5 = getScoresUseCase.fetchAndCacheData()
        emitProgressIfSuccess(result5)
        val result6 = getReportTypesUseCase.fetchAndCacheData()
        emitProgressIfSuccess(result6)
    }

    private fun resetProgress() {
        progress = State.Success(0.0f)
    }

    private suspend fun FlowCollector<State<Float>>.emitProgressIfSuccess(result1: State<*>) {
        if (result1 is State.Success) {
            progress = State.Success(progress.data?.plus(SINGLE_PROGRESS_INCREMENT) ?: NO_PROGRESS)
            emit(progress)
        } else {
            resetProgress()
            emit(
                State.Error(result1.error)
            )
        }
    }

    companion object {
        const val NO_PROGRESS = 0.0f
        const val SINGLE_PROGRESS_INCREMENT = 0.25f
    }
}
