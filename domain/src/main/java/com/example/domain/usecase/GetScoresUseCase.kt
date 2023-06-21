package com.example.domain.usecase

import com.example.data.local.IQuizLocalRepository
import com.example.data.remote.IQuizRemoteRepository
import com.example.data.remote.dto.ScoreDto
import com.example.domain.State
import com.example.domain.mapper.ScoreDtoMapper
import com.example.domain.mapper.ScoresEntityMapper
import com.example.domain.model.ScoreModel
import com.example.domain.network.IFetchAndCacheManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface IGetScoresUseCase : IFetchAndCacheUseCase<Unit, List<ScoreModel>> {
    operator fun invoke(): Flow<List<ScoreModel>>
}

class GetScoresUseCase(
    private val fetchAndCacheManager: IFetchAndCacheManager,
    private val remoteRepository: IQuizRemoteRepository,
    private val localRepository: IQuizLocalRepository,
    private val mapper: ScoreDtoMapper,
    private val domainMapper: ScoresEntityMapper
) : IGetScoresUseCase {

    override operator fun invoke(): Flow<List<ScoreModel>> = localRepository.readScores().map {
        domainMapper.map(it)
    }

    override suspend fun fetchAndCache(isForceRefresh: Boolean, input: Unit?): State<List<ScoreModel>> {
        return fetchAndCacheManager(
            shouldFetch = { it.isEmpty() || isForceRefresh },
            query = { localRepository.readScores() },
            fetch = { remoteRepository.getScores() },
            cache = {
                val list = mapper.map(getScoreDtosWithRankings(it))
                localRepository.insertScores(list)
            },
            mapToDomainModel = { domainMapper.map(it) }
        )
    }

    private fun getScoreDtosWithRankings(dtos: List<ScoreDto>): List<Pair<ScoreDto, Int>> {
        return dtos.groupBy { it.mode }.values.flatMap { list ->
            list.sortedByDescending { it.score }.mapIndexed { index, scoreDto ->
                Pair(scoreDto, index + 1)
            }
        }
    }
}
