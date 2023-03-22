package com.example.data.local.entity.mapper

import com.example.data.local.entity.ResultRecordEntity
import com.example.data.remote.dto.ResultRecordDto
import com.example.util.mapper.DataMapper

class ResultRecordEntityMapper : DataMapper<ResultRecordEntity, ResultRecordDto> {
    override fun map(input: ResultRecordEntity): ResultRecordDto {
        return ResultRecordDto(username = input.username, mode = input.modeId, score = input.points)
    }
}
