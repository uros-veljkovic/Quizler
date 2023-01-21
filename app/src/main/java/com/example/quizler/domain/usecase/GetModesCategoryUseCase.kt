package com.example.quizler.domain.usecase

import com.example.quizler.data.local.entity.CategoryModeEntity
import com.example.quizler.data.remote.QRealRemoteRepo
import com.example.quizler.data.remote.dto.CategoryModesDto
import com.example.quizler.domain.data.local.IQuizLocalRepository
import com.example.quizler.domain.data.remote.IQuizRemoteRepository
import com.example.quizler.util.INetworkActionHandler
import com.example.quizler.util.State
import com.example.quizler.util.mapper.DataMapper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetModesCategoryUseCase @Inject constructor(
    private val networkActionHandler: INetworkActionHandler,
    @QRealRemoteRepo private val remoteRepository: IQuizRemoteRepository,
    private val localRepository: IQuizLocalRepository,
    private val mapper: DataMapper<CategoryModesDto, List<CategoryModeEntity>>
) : IFetchAndCacheUseCase {

    operator fun invoke(): Flow<List<CategoryModeEntity>> = localRepository.readCategoriesModes()

    override suspend fun fetchAndCache(isForceRefresh: Boolean): State<Unit> {
        return networkActionHandler.fetchAndCache(
            query = { localRepository.readCategoriesModes() },
            shouldFetch = { it.isEmpty() },
            fetch = { remoteRepository.getCategoryModes() },
            cache = { localRepository.insertCategoriesModes(mapper.map(it)) }
        )
    }
}
