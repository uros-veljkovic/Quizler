package com.example.domain.usecase

import com.example.data.local.IQuizLocalRepository
import com.example.data.remote.IQuizRemoteRepository
import com.example.domain.State
import com.example.domain.mapper.CategoryModeDtoMapper
import com.example.domain.mapper.CategoryModeMapper
import com.example.domain.model.mode.CategoryMode
import com.example.domain.network.IFetchAndCacheManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface IGetModesCategoryUseCase : IFetchAndCacheUseCase {
    operator fun invoke(): Flow<List<CategoryMode>>
}

class GetModesCategoryUseCase(
    private val fetchAndCacheManager: IFetchAndCacheManager,
    private val remoteRepository: IQuizRemoteRepository,
    private val localRepository: IQuizLocalRepository,
    private val dtoMapper: CategoryModeDtoMapper,
    private val uiMapper: CategoryModeMapper
) : IGetModesCategoryUseCase {

    override fun invoke(): Flow<List<CategoryMode>> = localRepository.readCategoriesModes().map {
        uiMapper.map(it)
    }

    override suspend fun fetchAndCache(isForceRefresh: Boolean): State<Unit> {
        return fetchAndCacheManager(
            query = { localRepository.readCategoriesModes() },
            shouldFetch = { it.isEmpty() },
            fetch = { remoteRepository.getCategoryModes() },
            cache = { localRepository.insertCategoriesModes(dtoMapper.map(it)) }
        )
    }
}
