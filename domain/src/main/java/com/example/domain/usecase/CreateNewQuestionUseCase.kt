package com.example.domain.usecase

import com.example.data.remote.IQuizRemoteRepository
import com.example.domain.State
import com.example.domain.mapper.CreateNewQuestionMappers
import com.example.domain.model.CreateNewQuestionBundle
import com.example.util.data.RepositoryResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface ICreateNewQuestionUseCase {
    suspend operator fun invoke(question: CreateNewQuestionBundle): Flow<State<Unit>>
}

class CreateNewQuestionUseCase(
    private val remoteRepository: IQuizRemoteRepository,
    private val mapper: CreateNewQuestionMappers,
) : ICreateNewQuestionUseCase {

    override suspend operator fun invoke(question: CreateNewQuestionBundle) = flow {
        emit(State.Loading())
        delay(500)
        when (val result = remoteRepository.create(mapper.map(question))) {
            is RepositoryResponse.Failure -> emit(State.Error(throwable = result.throwable))
            is RepositoryResponse.Success -> emit(State.Success(Unit))
        }
    }
}
