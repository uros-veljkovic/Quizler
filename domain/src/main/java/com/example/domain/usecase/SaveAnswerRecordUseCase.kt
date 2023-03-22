package com.example.domain.usecase

import com.example.data.local.IQuizLocalRepository
import com.example.data.remote.IQuizRemoteRepository
import com.example.domain.mapper.AnswerRecordDomainMapper
import com.example.domain.mapper.AnswerRecordDtoMapper
import com.example.domain.model.AnswerRecord
import com.example.util.data.RepositoryResponse

interface ISaveAnswerRecordUseCase {
    suspend operator fun invoke(record: AnswerRecord)
}

class SaveAnswerRecordUseCase(
    private val remoteRepository: IQuizRemoteRepository,
    private val localRepository: IQuizLocalRepository,
    private val dtoMapper: AnswerRecordDtoMapper,
    private val entityMapper: AnswerRecordDomainMapper
) : ISaveAnswerRecordUseCase {
    override suspend operator fun invoke(record: AnswerRecord) {
        val result = remoteRepository.record(dtoMapper.map(record))
        if (result is RepositoryResponse.Failure) {
            localRepository.insertAnswerRecord(entityMapper.map(record))
        }
    }
}
