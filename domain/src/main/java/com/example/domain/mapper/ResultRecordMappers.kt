package com.example.domain.mapper

import com.example.data.local.entity.ResultRecordEntity
import com.example.data.remote.dto.ResultRecordDto
import com.example.domain.model.ResultRecord
import com.example.util.mapper.DataMapper

class ResultRecordDtoMapper : DataMapper<ResultRecordDto, ResultRecordEntity> {
    override fun map(input: ResultRecordDto): ResultRecordEntity {
        return ResultRecordEntity(
            username = input.username,
            modeId = input.mode,
            points = input.score
        )
    }
}

class ResultRecordEntityMapper : DataMapper<ResultRecord, ResultRecordEntity> {
    override fun map(input: ResultRecord): ResultRecordEntity {
        return ResultRecordEntity(
            username = input.username,
            modeId = input.mode,
            points = input.score
        )
    }
}

class ResultRecordMapper : DataMapper<ResultRecord, ResultRecordDto> {
    override fun map(input: ResultRecord): ResultRecordDto {
        return ResultRecordDto(
            username = input.username,
            mode = input.mode,
            score = input.score
        )
    }
}
