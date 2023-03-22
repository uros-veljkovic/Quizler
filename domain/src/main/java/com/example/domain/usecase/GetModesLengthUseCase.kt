package com.example.domain.usecase

import com.example.data.local.IQuizLocalRepository
import com.example.data.remote.IQuizRemoteRepository
import com.example.domain.State
import com.example.domain.mapper.LengthModeDtoMapper
import com.example.domain.mapper.LengthModeMapper
import com.example.domain.model.mode.LengthMode
import com.example.domain.network.IFetchAndCacheManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface IGetModesLengthUseCase : IFetchAndCacheUseCase {
    operator fun invoke(): Flow<List<LengthMode>>
}

class GetModesLengthUseCase(
    private val fetchAndCacheManager: IFetchAndCacheManager,
    private val remoteRepository: IQuizRemoteRepository,
    private val localRepository: IQuizLocalRepository,
    private val dtoMapper: LengthModeDtoMapper,
    private val domainMapper: LengthModeMapper,
) : IGetModesLengthUseCase {

    override operator fun invoke(): Flow<List<LengthMode>> = localRepository.readLengthModes().map {
        domainMapper.map(it)
    }

    override suspend fun fetchAndCache(isForceRefresh: Boolean): State<Unit> {
        return fetchAndCacheManager(
            query = { localRepository.readLengthModes() },
            shouldFetch = { it.isEmpty() },
            fetch = { remoteRepository.getLengthModes() },
            cache = {
                val mappedData = dtoMapper.map(it)
                localRepository.insertLengthModes(mappedData)
            }
        )
    }
}
