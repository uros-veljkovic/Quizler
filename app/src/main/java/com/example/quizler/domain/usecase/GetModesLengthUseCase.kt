package com.example.quizler.domain.usecase

import com.example.quizler.data.local.entity.LengthModeEntity
import com.example.quizler.data.remote.QRealRemoteRepo
import com.example.quizler.data.remote.dto.LengthModesDto
import com.example.quizler.domain.data.local.IQuizLocalRepository
import com.example.quizler.domain.data.remote.IQuizRemoteRepository
import com.example.quizler.util.INetworkActionHandler
import com.example.quizler.util.State
import com.example.quizler.util.mapper.DataMapper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetModesLengthUseCase @Inject constructor(
    private val networkActionHandler: INetworkActionHandler,
    @QRealRemoteRepo private val remoteRepository: IQuizRemoteRepository,
    private val localRepository: IQuizLocalRepository,
    private val mapper: DataMapper<LengthModesDto, List<LengthModeEntity>>,
) : IFetchAndCacheUseCase {

    operator fun invoke(): Flow<List<LengthModeEntity>> = localRepository.readLengthModes()

    override suspend fun fetchAndCache(): State<Unit> {
        return networkActionHandler.fetchAndCache(
            query = { localRepository.readLengthModes() },
            shouldFetch = { it.isEmpty() },
            fetch = { remoteRepository.getLengthModes() },
            cache = { localRepository.insertLengthModes(mapper.map(it)) }
        )
    }
}
