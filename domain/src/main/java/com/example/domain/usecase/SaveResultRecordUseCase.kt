package com.example.domain.usecase

import com.example.data.local.IQuizLocalRepository
import com.example.data.remote.IQuizRemoteRepository
import com.example.domain.mapper.ResultRecordEntityMapper
import com.example.domain.mapper.ResultRecordMapper
import com.example.domain.model.ResultRecord
import com.example.util.data.RepositoryResponse

interface ISaveResultRecordUseCase {
    suspend operator fun invoke(record: ResultRecord)
}

class SaveResultRecordUseCase(
    private val getScoresUseCase: IGetScoresUseCase,
    private val remoteRepository: IQuizRemoteRepository,
    private val localRepository: IQuizLocalRepository,
    private val entityMapper: ResultRecordEntityMapper,
    private val domainMapper: ResultRecordMapper
) : ISaveResultRecordUseCase {
    override suspend operator fun invoke(record: ResultRecord) {
        val result = remoteRepository.record(domainMapper.map(record))
        if (result is RepositoryResponse.Failure) {
            localRepository.insertResultRecord(entityMapper.map(record))
        } else {
            getScoresUseCase.fetchAndCache(true)
        }
    }
}
