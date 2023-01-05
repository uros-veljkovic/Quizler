package com.example.quizler.data.remote.dto.mapper

import com.example.quizler.data.local.entity.ResultRecordEntity
import com.example.quizler.data.remote.dto.ResultRecordDto
import com.example.quizler.util.mapper.DataMapper

class ResultRecordDtoMapper : DataMapper<ResultRecordDto, ResultRecordEntity> {
    override fun map(input: ResultRecordDto): ResultRecordEntity {
        return ResultRecordEntity(
            username = input.username,
            modeId = input.mode,
            points = input.score
        )
    }
}
