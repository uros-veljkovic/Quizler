package com.example.quizler.domain.usecase

import com.example.quizler.data.local.entity.AnswerRecordEntity
import com.example.quizler.data.local.entity.ResultRecordEntity
import com.example.quizler.data.remote.QRealRemoteRepo
import com.example.quizler.data.remote.dto.AnswerRecordDto
import com.example.quizler.data.remote.dto.ResultRecordDto
import com.example.quizler.domain.data.RepositoryResponse
import com.example.quizler.domain.data.local.IQuizLocalRepository
import com.example.quizler.domain.data.remote.IQuizRemoteRepository
import com.example.quizler.util.State
import com.example.quizler.util.mapper.DataMapper
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class SendStoredDataToServerUseCase @Inject constructor(
    @QRealRemoteRepo private val remoteRepository: IQuizRemoteRepository,
    private val localRepository: IQuizLocalRepository,
    private val answerMapper: DataMapper<AnswerRecordEntity, AnswerRecordDto>,
    private val resultMapper: DataMapper<ResultRecordEntity, ResultRecordDto>,
) {

    suspend operator fun invoke(): Boolean {
        return State.isAllSuccess(
            handleSavedResultRecords(),
            handleSavedAnswerRecords(),
            handleSavedReportedQuestions(),
        )
    }

    private suspend fun handleSavedResultRecords(): State<Unit> {
        val entities = localRepository.readResultRecords().first()
        if (entities.isNotEmpty()) {
            entities.forEach {
                if (remoteRepository.record(resultMapper.map(it)) is RepositoryResponse.Failure) {
                    return State.Error()
                }
            }
            localRepository.deleteAllResultRecords()
        }
        return State.Success(Unit)
    }

    private suspend fun handleSavedAnswerRecords(): State<Unit> {
        val entities = localRepository.readAnswerRecords().first()
        if (entities.isNotEmpty()) {
            entities.forEach {
                if (remoteRepository.record(answerMapper.map(it)) is RepositoryResponse.Failure) {
                    return State.Error()
                }
            }
            localRepository.deleteAllAnswerRecords()
        }
        return State.Success(Unit)
    }

    private suspend fun handleSavedReportedQuestions(): State<Unit> {
        val entities = localRepository.readReportedQuestions().first()
        if (entities.isNotEmpty()) {
            entities.forEach {
                if (remoteRepository.report(it.questionId) is RepositoryResponse.Failure) {
                    return State.Error()
                }
            }
            localRepository.deleteAllAnswerRecords()
        }
        return State.Success(Unit)
    }

    companion object {
        const val TAG = "SendStoredDataToServerUseCase"
    }
}
