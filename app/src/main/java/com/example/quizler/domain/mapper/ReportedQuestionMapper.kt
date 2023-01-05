package com.example.quizler.domain.mapper

import com.example.quizler.data.local.entity.ReportedQuestionEntity
import com.example.quizler.domain.usecase.ReportedQuestion
import com.example.quizler.util.mapper.DataMapper

class ReportedQuestionMapper : DataMapper<ReportedQuestion, ReportedQuestionEntity> {
    override fun map(input: ReportedQuestion): ReportedQuestionEntity {
        return ReportedQuestionEntity(
            id = input.id
        )
    }
}
