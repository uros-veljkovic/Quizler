package com.example.quizler.domain.usecase

import com.example.quizler.data.local.entity.QuestionWithAnswersEntity
import com.example.quizler.data.remote.QRealRemoteRepo
import com.example.quizler.data.remote.dto.QuestionDto
import com.example.quizler.domain.data.local.IQuizLocalRepository
import com.example.quizler.domain.data.remote.IQuizRemoteRepository
import com.example.quizler.ui.screen.quiz.QuestionBundle
import com.example.quizler.util.INetworkActionHandler
import com.example.quizler.util.State
import com.example.quizler.util.mapper.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetQuestionsUseCase @Inject constructor(
    private val networkActionHandler: INetworkActionHandler,
    @QRealRemoteRepo private val remoteRepository: IQuizRemoteRepository,
    private val localRepository: IQuizLocalRepository,
    private val dtoMapper: DataMapper<QuestionDto, QuestionWithAnswersEntity>,
    private val uiMapper: DataMapper<QuestionWithAnswersEntity, QuestionBundle>
) {

    operator fun invoke(): Flow<List<QuestionBundle>> = localRepository.readQuestionsWithAnswers().map { uiMapper.map(it) }

    suspend fun fetchAndCacheData(): State<List<QuestionWithAnswersEntity>> {
        return networkActionHandler.fetchAndCache(
            query = { localRepository.readQuestionsWithAnswers() },
            shouldFetch = { it.isEmpty() },
            fetch = { remoteRepository.getQuestions(false) },
            cache = { localRepository.insertQuestionsWithAnswers(dtoMapper.map(it)) }
        )
    }
}
