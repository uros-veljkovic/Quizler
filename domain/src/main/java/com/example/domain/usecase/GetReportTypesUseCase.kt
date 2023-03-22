package com.example.domain.usecase

import com.example.data.local.IQuizLocalRepository
import com.example.data.remote.IQuizRemoteRepository
import com.example.domain.State
import com.example.domain.mapper.ReportTypeDtoMapper
import com.example.domain.mapper.ReportTypeUiMapper
import com.example.domain.model.ReportType
import com.example.domain.network.IFetchAndCacheManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface IGetReportTypesUseCase : IFetchAndCacheUseCase {
    operator fun invoke(): Flow<List<ReportType>>
}

class GetReportTypesUseCase(
    private val fetchAndCacheManager: IFetchAndCacheManager,
    private val remoteRepository: IQuizRemoteRepository,
    private val localRepository: IQuizLocalRepository,
    private val mapper: ReportTypeDtoMapper,
    private val uiMapper: ReportTypeUiMapper,
) : IGetReportTypesUseCase {

    override operator fun invoke(): Flow<List<ReportType>> = localRepository.readReportTypes().map { uiMapper.map(it) }

    override suspend fun fetchAndCache(isForceRefresh: Boolean): State<Unit> {
        return fetchAndCacheManager(
            query = { localRepository.readReportTypes() },
            shouldFetch = { it.isEmpty() },
            fetch = { remoteRepository.getReportTypes() },
            cache = { localRepository.insertReportTypes(mapper.map(it)) }
        )
    }
}
