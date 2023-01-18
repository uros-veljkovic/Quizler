package com.example.quizler.domain.mapper

import com.example.quizler.data.local.entity.InvalidQuestionReportEntity
import com.example.quizler.domain.model.InvalidQuestionReport
import com.example.quizler.util.mapper.DataMapper

class ReportedQuestionMapper : DataMapper<InvalidQuestionReport, InvalidQuestionReportEntity> {
    override fun map(input: InvalidQuestionReport): InvalidQuestionReportEntity {
        return InvalidQuestionReportEntity(
            questionId = input.questionId,
            reportTypeId = input.reportTypeId
        )
    }
}
