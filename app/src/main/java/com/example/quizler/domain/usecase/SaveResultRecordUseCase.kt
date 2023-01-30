package com.example.quizler.domain.usecase

import com.example.quizler.data.local.entity.ResultRecordEntity
import com.example.quizler.data.remote.QRealRemoteRepo
import com.example.quizler.data.remote.dto.ResultRecordDto
import com.example.quizler.domain.data.RepositoryResponse
import com.example.quizler.domain.data.local.IQuizLocalRepository
import com.example.quizler.domain.data.remote.IQuizRemoteRepository
import com.example.quizler.util.mapper.DataMapper
import javax.inject.Inject

class SaveResultRecordUseCase @Inject constructor(
    private val getScoresUseCase: GetScoresUseCase,
    @QRealRemoteRepo private val remoteRepository: IQuizRemoteRepository,
    private val localRepository: IQuizLocalRepository,
    private val mapper: DataMapper<ResultRecordDto, ResultRecordEntity>
) {
    suspend operator fun invoke(record: ResultRecordDto) {
        val result = remoteRepository.record(record)
        if (result is RepositoryResponse.Failure) {
            localRepository.insertResultRecord(mapper.map(record))
        } else {
            getScoresUseCase.fetchAndCache(true)
        }
    }
}
