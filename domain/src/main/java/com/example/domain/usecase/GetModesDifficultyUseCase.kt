package com.example.domain.usecase

import com.example.data.local.IQuizLocalRepository
import com.example.data.remote.IQuizRemoteRepository
import com.example.domain.State
import com.example.domain.mapper.DifficultyModeDtoMapper
import com.example.domain.mapper.DifficultyModeMapper
import com.example.domain.model.mode.DifficultyMode
import com.example.domain.network.IFetchAndCacheManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface IGetModesDifficultyUseCase : IFetchAndCacheUseCase {
    operator fun invoke(): Flow<List<DifficultyMode>>
}

class GetModesDifficultyUseCase(
    private val fetchAndCacheManager: IFetchAndCacheManager,
    private val remoteRepository: IQuizRemoteRepository,
    private val localRepository: IQuizLocalRepository,
    private val mapper: DifficultyModeDtoMapper,
    private val domainMapper: DifficultyModeMapper
) : IGetModesDifficultyUseCase {

    override operator fun invoke(): Flow<List<DifficultyMode>> =
        localRepository.readDifficultyModes().map {
            domainMapper.map(it)
        }

    override suspend fun fetchAndCache(isForceRefresh: Boolean): State<Unit> {
        return fetchAndCacheManager(
            query = { localRepository.readDifficultyModes() },
            shouldFetch = { it.isEmpty() },
            fetch = { remoteRepository.getDifficultyModes() },
            cache = { localRepository.insertDifficultyModes(mapper.map(it)) }
        )
    }
}
