package com.example.quizler.domain.usecase

import com.example.quizler.data.local.entity.InvalidQuestionReportEntity
import com.example.quizler.data.remote.QRealRemoteRepo
import com.example.quizler.data.remote.dto.ReportQuestionDto
import com.example.quizler.domain.data.RepositoryResponse
import com.example.quizler.domain.data.local.IQuizLocalRepository
import com.example.quizler.domain.data.remote.IQuizRemoteRepository
import com.example.quizler.domain.model.InvalidQuestionReport
import com.example.quizler.util.mapper.DataMapper
import javax.inject.Inject

class SendInvalidQuestionReportUseCase @Inject constructor(
    @QRealRemoteRepo
    private val remoteRepository: IQuizRemoteRepository,
    private val localRepository: IQuizLocalRepository,
    private val mapper: DataMapper<InvalidQuestionReport, InvalidQuestionReportEntity>
) {

    suspend operator fun invoke(report: InvalidQuestionReport) {
        val result = remoteRepository.report(ReportQuestionDto(reportTypeId = report.reportTypeId, questionId = report.questionId))
        if (result is RepositoryResponse.Failure) {
            localRepository.insertReportedQuestion(mapper.map(report))
        }
    }
}
