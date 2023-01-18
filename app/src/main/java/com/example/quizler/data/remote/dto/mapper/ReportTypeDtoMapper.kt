package com.example.quizler.data.remote.dto.mapper

import com.example.quizler.data.local.entity.ReportTypeEntity
import com.example.quizler.data.remote.dto.ReportTypeDto
import com.example.quizler.util.mapper.DataMapper
import com.example.quizler.util.provider.AbstractResourceProvider

class ReportTypeDtoMapper(
    private val resourceProvider: AbstractResourceProvider<String>
) : DataMapper<ReportTypeDto, ReportTypeEntity> {
    override fun map(input: ReportTypeDto): ReportTypeEntity {
        return ReportTypeEntity(
            id = input.id,
            type = resourceProvider.getResourceByName(input.type) ?: "?"
        )
    }
}
