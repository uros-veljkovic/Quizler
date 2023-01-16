package com.example.quizler.domain.usecase

import com.example.quizler.data.local.entity.ScoreEntity
import com.example.quizler.data.remote.QRealRemoteRepo
import com.example.quizler.data.remote.dto.ScoreDto
import com.example.quizler.domain.data.local.IQuizLocalRepository
import com.example.quizler.domain.data.remote.IQuizRemoteRepository
import com.example.quizler.ui.model.Score
import com.example.quizler.util.INetworkActionHandler
import com.example.quizler.util.State
import com.example.quizler.util.mapper.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetScoresUseCase @Inject constructor(
    private val networkActionHandler: INetworkActionHandler,
    @QRealRemoteRepo private val remoteRepository: IQuizRemoteRepository,
    private val localRepository: IQuizLocalRepository,
    private val mapper: DataMapper<Pair<ScoreDto, Int>, ScoreEntity>,
    private val uiMapper: DataMapper<ScoreEntity, Score>
) : IFetchAndCacheUseCase {

    operator fun invoke(): Flow<List<Score>> = localRepository.readScores().map { uiMapper.map(it) }

    override suspend fun fetchAndCache(): State<Unit> {
        return networkActionHandler.fetchAndCache(
            shouldFetch = { true },
            query = { localRepository.readScores() },
            fetch = { remoteRepository.getScores() },
            cache = { localRepository.insertScores(mapper.map(getScoreDtosWithRankings(it))) }
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
