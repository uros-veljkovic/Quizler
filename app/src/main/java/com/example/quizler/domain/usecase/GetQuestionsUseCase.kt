package com.example.quizler.domain.usecase

import com.example.quizler.data.local.entity.QuestionWithAnswersEntity
import com.example.quizler.data.remote.QRealRemoteRepo
import com.example.quizler.data.remote.dto.QuestionDto
import com.example.quizler.domain.data.local.IQuizLocalRepository
import com.example.quizler.domain.data.remote.IQuizRemoteRepository
import com.example.quizler.domain.date.IDateManager
import com.example.quizler.domain.preferences.IDataSyncCoordinator
import com.example.quizler.ui.screen.quiz.QuestionBundle
import com.example.quizler.util.INetworkActionHandler
import com.example.quizler.util.State
import com.example.quizler.util.mapper.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.util.Date
import javax.inject.Inject

class GetQuestionsUseCase @Inject constructor(
    private val networkActionHandler: INetworkActionHandler,
    @QRealRemoteRepo private val remoteRepository: IQuizRemoteRepository,
    private val localRepository: IQuizLocalRepository,
    private val dataSyncCoordinator: IDataSyncCoordinator,
    private val dateManager: IDateManager,
    private val dtoMapper: DataMapper<QuestionDto, QuestionWithAnswersEntity>,
    private val uiMapper: DataMapper<QuestionWithAnswersEntity, QuestionBundle>
) {

    // TODO: Filter questions by isApproved
    operator fun invoke(isApproved: Boolean = true): Flow<List<QuestionBundle>> =
        localRepository.readQuestionsWithAnswers()
            .map { list ->
                list.filter { it.question.isApproved == isApproved }.map { uiMapper.map(it) }
            }

    suspend fun fetchAndCacheData(): State<List<QuestionWithAnswersEntity>> {
        val hasPast30Days = dateManager.hasPast(30, dataSyncCoordinator.getDataSyncTime().first())

        return networkActionHandler.fetchAndCache(
            query = { localRepository.readQuestionsWithAnswers() },
            shouldFetch = { it.isEmpty() || hasPast30Days },
            fetch = { remoteRepository.getQuestions() },
            cache = {
                refreshTimeDataSynced()
                localRepository.insertQuestionsWithAnswers(dtoMapper.map(it))
            }
        )
    }

    private suspend fun refreshTimeDataSynced() {
        dataSyncCoordinator.setDataSyncTime(Date().time)
    }
}
