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
) : IFetchAndCacheUseCase {

    operator fun invoke(): Flow<List<ReportTypeEntity>> = localRepository.readReportTypes()

    override suspend fun fetchAndCache(): State<Unit> {
        return networkActionHandler.fetchAndCache(
            query = { localRepository.readReportTypes() },
            shouldFetch = { it.isEmpty() },
            fetch = { remoteRepository.getReportTypes() },
            cache = { localRepository.insertReportTypes(mapper.map(it)) }
        )
    }
}

class ReportTypeDtoMapper : DataMapper<ReportTypeDto, ReportTypeEntity> {
    override fun map(input: ReportTypeDto): ReportTypeEntity {
        return ReportTypeEntity(
            id = input.id,
            type = input.type
        )
    }
}
