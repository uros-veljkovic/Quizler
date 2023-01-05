package com.example.quizler.domain.usecase

import com.example.quizler.data.local.entity.ReportedQuestionEntity
import com.example.quizler.data.remote.QRealRemoteRepo
import com.example.quizler.domain.data.RepositoryResponse
import com.example.quizler.domain.data.local.IQuizLocalRepository
import com.example.quizler.domain.data.remote.IQuizRemoteRepository
import com.example.quizler.util.mapper.DataMapper
import javax.inject.Inject

class ReportQuestionUseCase @Inject constructor(
    @QRealRemoteRepo
    private val remoteRepository: IQuizRemoteRepository,
    private val localRepository: IQuizLocalRepository,
    private val mapper: DataMapper<ReportedQuestion, ReportedQuestionEntity>
) {

    suspend operator fun invoke(question: ReportedQuestion) {
        val result = remoteRepository.report(question.id)
        if (result is RepositoryResponse.Failure) {
            localRepository.insertReportedQuestion(mapper.map(question))
        }
    }
}

data class ReportedQuestion(
    val id: String
)
