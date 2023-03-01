package com.example.quizler.domain.usecase

import com.example.quizler.data.remote.QRealRemoteRepo
import com.example.quizler.data.remote.dto.QuestionDto
import com.example.quizler.domain.data.RepositoryResponse
import com.example.quizler.domain.data.remote.IQuizRemoteRepository
import com.example.quizler.domain.model.Question
import com.example.quizler.util.State
import com.example.quizler.util.mapper.DataMapper
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CreateNewQuestionUseCase @Inject constructor(
    @QRealRemoteRepo
    private val remoteRepository: IQuizRemoteRepository,
    private val mapper: DataMapper<Question, QuestionDto>
) {

    suspend operator fun invoke(question: Question) = flow {
        emit(State.Loading())
        delay(500)
        when (remoteRepository.create(mapper.map(question))) {
            is RepositoryResponse.Failure -> emit(State.Error())
            is RepositoryResponse.Success -> emit(State.Success(Unit))
        }
    }
}
