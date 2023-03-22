package com.example.domain.mapper

import com.example.data.local.entity.InvalidQuestionReportEntity
import com.example.domain.model.InvalidQuestionReport
import com.example.util.mapper.DataMapper

class ReportedQuestionMapper : DataMapper<InvalidQuestionReport, InvalidQuestionReportEntity> {
    override fun map(input: InvalidQuestionReport): InvalidQuestionReportEntity {
        return InvalidQuestionReportEntity(
            questionId = input.questionId,
            reportTypeId = input.reportTypeId
        )
    }
}
