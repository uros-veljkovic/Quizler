package com.example.domain.usecase

import com.example.data.local.IQuizLocalRepository
import com.example.data.remote.IQuizRemoteRepository
import com.example.data.remote.dto.ReportQuestionDto
import com.example.domain.mapper.ReportedQuestionMapper
import com.example.domain.model.InvalidQuestionReport
import com.example.util.data.RepositoryResponse

interface ISendInvalidQuestionReportUseCase {
    suspend operator fun invoke(report: InvalidQuestionReport)
}

class SendInvalidQuestionReportUseCase(
    private val remoteRepository: IQuizRemoteRepository,
    private val localRepository: IQuizLocalRepository,
    private val mapper: ReportedQuestionMapper
) : ISendInvalidQuestionReportUseCase {

    override suspend operator fun invoke(report: InvalidQuestionReport) {
        val result = remoteRepository.report(
            ReportQuestionDto(
                reportTypeId = report.reportTypeId,
                questionId = report.questionId
            )
        )
        if (result is RepositoryResponse.Failure) {
            localRepository.insertReportedQuestion(mapper.map(report))
        }
    }
}
