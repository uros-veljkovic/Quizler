package com.example.quizler.ui.screen.quiz.mapper

import com.example.quizler.data.local.entity.ReportTypeEntity
import com.example.quizler.ui.model.ReportType
import com.example.quizler.util.mapper.DataMapper

class ReportTypeUiMapper : DataMapper<ReportTypeEntity, ReportType> {
    // FIXME: Resolve title thorugh report type
    override fun map(input: ReportTypeEntity): ReportType {
        return ReportType(id = input.id, title = input.type, isSelected = false)
    }
}
