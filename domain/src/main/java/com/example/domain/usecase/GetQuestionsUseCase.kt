package com.example.domain.usecase

import com.example.data.local.IQuizLocalRepository
import com.example.data.preferences.IDataSyncCoordinator
import com.example.data.remote.IQuizRemoteRepository
import com.example.domain.State
import com.example.domain.date.IDateManager
import com.example.domain.mapper.QuestionWithAnswersDtoMapper
import com.example.domain.mapper.QuestionWithAnswersUiMapper
import com.example.domain.model.QuestionWithAnswers
import com.example.domain.network.IFetchAndCacheManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.util.Date

interface IGetQuestionsUseCase : IFetchAndCacheUseCase<Unit, List<QuestionWithAnswers>> {
    operator fun invoke(isApproved: Boolean = true): Flow<List<QuestionWithAnswers>>
}

class GetQuestionsUseCase(
    private val fetchAndCacheManager: IFetchAndCacheManager,
    private val remoteRepository: IQuizRemoteRepository,
    private val localRepository: IQuizLocalRepository,
    private val dataSyncCoordinator: IDataSyncCoordinator,
    private val dateManager: IDateManager,
    private val dtoMapper: QuestionWithAnswersDtoMapper,
    private val domainMapper: QuestionWithAnswersUiMapper
) : IGetQuestionsUseCase {

    override operator fun invoke(isApproved: Boolean): Flow<List<QuestionWithAnswers>> =
        localRepository.readQuestionsWithAnswers().map { list ->
            list.filter {
                it.question.isApproved == isApproved
            }.map {
                domainMapper.map(it)
            }
        }

    override suspend fun fetchAndCache(isForceRefresh: Boolean, input: Unit?): State<List<QuestionWithAnswers>> {
        val hasPast30Days = dateManager.hasDaysPassed(dataSyncCoordinator.getDataSyncTime().first(), 30)
        return fetchAndCacheManager(
            query = { localRepository.readQuestionsWithAnswers() },
            shouldFetch = { it.isEmpty() || hasPast30Days || isForceRefresh },
            fetch = { remoteRepository.getQuestions() },
            cache = {
                refreshTimeDataSynced()
                localRepository.insertQuestionsWithAnswers(dtoMapper.map(it))
            },
            mapToDomainModel = { domainMapper.map(it) }
        )
    }

    private suspend fun refreshTimeDataSynced() {
        dataSyncCoordinator.setDataSyncTime(Date().time)
    }
}
