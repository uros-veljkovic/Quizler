package com.example.quizler.domain.usecase

import com.example.quizler.data.remote.QRealRemoteRepo
import com.example.quizler.domain.data.RepositoryResponse
import com.example.quizler.domain.data.remote.IQuizRemoteRepository
import com.example.quizler.data.remote.dto.CreateNewQuestionDto
import com.example.quizler.ui.screen.newquestion.CreateNewQuestionScreenState
import com.example.quizler.util.State
import com.example.quizler.util.mapper.DataMapper
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CreateNewQuestionUseCase @Inject constructor(
    @QRealRemoteRepo
    private val remoteRepository: IQuizRemoteRepository,
    private val mapper: DataMapper<CreateNewQuestionScreenState, CreateNewQuestionDto>,

    ) {

    suspend operator fun invoke(question: CreateNewQuestionScreenState) = flow {
        emit(State.Loading())
        delay(500)
        when (remoteRepository.create(mapper.map(question))) {
            is RepositoryResponse.Failure -> emit(State.Error())
            is RepositoryResponse.Success -> emit(State.Success(Unit))
        }
    }
}