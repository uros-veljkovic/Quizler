package com.example.domain.usecase

import com.example.data.local.IQuizLocalRepository
import com.example.data.local.entity.mapper.AnswerRecordEntityMapper
import com.example.data.local.entity.mapper.ResultRecordEntityMapper
import com.example.data.remote.IQuizRemoteRepository
import com.example.domain.State
import com.example.util.data.RepositoryResponse
import kotlinx.coroutines.flow.first

interface ISendStoredDataToServerUseCase {
    suspend operator fun invoke(): Boolean
}

class SendStoredDataToServerUseCase(
    private val remoteRepository: IQuizRemoteRepository,
    private val localRepository: IQuizLocalRepository,
    private val answerMapper: AnswerRecordEntityMapper,
    private val resultMapper: ResultRecordEntityMapper,
) : ISendStoredDataToServerUseCase {

    override suspend operator fun invoke(): Boolean {
        return State.isAllSuccess(
            handleSavedResultRecords(),
            handleSavedAnswerRecords()
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
}
