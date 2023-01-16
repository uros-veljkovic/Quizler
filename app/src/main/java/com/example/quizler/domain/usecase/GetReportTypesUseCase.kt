package com.example.quizler.domain.usecase

import com.example.quizler.data.local.entity.ReportTypeEntity
import com.example.quizler.data.remote.QRealRemoteRepo
import com.example.quizler.data.remote.dto.ReportTypeDto
import com.example.quizler.domain.data.local.IQuizLocalRepository
import com.example.quizler.domain.data.remote.IQuizRemoteRepository
import com.example.quizler.util.INetworkActionHandler
import com.example.quizler.util.State
import com.example.quizler.util.mapper.DataMapper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetReportTypesUseCase @Inject constructor(
    private val networkActionHandler: INetworkActionHandler,
    @QRealRemoteRepo private val remoteRepository: IQuizRemoteRepository,
    private val localRepository: IQuizLocalRepository,
    private val mapper: DataMapper<ReportTypeDto, ReportTypeEntity>,
) {

    operator fun invoke(): Flow<List<ReportTypeEntity>> = localRepository.readReportTypes()

    suspend fun fetchAndCacheData(): State<List<ReportTypeEntity>> {
        return networkActionHandler.fetchAndCache(
            query = { localRepository.readReportTypes() },
            shouldFetch = { it.isEmpty() },
            fetch = { remoteRepository.getReportTypes() },
            cache = { localRepository.insertReportTypes(mapper.map(it)) }
        )
    }
}
