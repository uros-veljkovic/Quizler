package com.example.quizler.data.local.entity.mapper

import com.example.quizler.data.local.entity.ResultRecordEntity
import com.example.quizler.data.remote.dto.ResultRecordDto
import com.example.quizler.util.mapper.DataMapper

class ResultRecordEntityMapper : DataMapper<ResultRecordEntity, ResultRecordDto> {
    override fun map(input: ResultRecordEntity): ResultRecordDto {
        return ResultRecordDto(username = input.username, mode = input.modeId, score = input.points)
    }
}
