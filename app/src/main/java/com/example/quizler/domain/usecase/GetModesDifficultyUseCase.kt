package com.example.quizler.domain.usecase

import com.example.quizler.data.local.entity.DifficultyModeEntity
import com.example.quizler.data.remote.QRealRemoteRepo
import com.example.quizler.data.remote.dto.DifficultyModesDto
import com.example.quizler.domain.data.local.IQuizLocalRepository
import com.example.quizler.domain.data.remote.IQuizRemoteRepository
import com.example.quizler.util.INetworkActionHandler
import com.example.quizler.util.State
import com.example.quizler.util.mapper.DataMapper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetModesDifficultyUseCase @Inject constructor(
    private val networkActionHandler: INetworkActionHandler,
    @QRealRemoteRepo private val remoteRepository: IQuizRemoteRepository,
    private val localRepository: IQuizLocalRepository,
    private val mapper: DataMapper<DifficultyModesDto, List<DifficultyModeEntity>>
) {

    operator fun invoke(): Flow<List<DifficultyModeEntity>> = localRepository.readDifficultyModes()

    suspend fun fetchAndCacheData(): State<List<DifficultyModeEntity>> {
        return networkActionHandler.fetchAndCache(
            query = { localRepository.readDifficultyModes() },
            shouldFetch = { it.isEmpty() },
            fetch = { remoteRepository.getDifficultyModes() },
            cache = { localRepository.insertDifficultyModes(mapper.map(it)) }
        )
    }
}
