package com.example.domain.mapper

import com.example.data.local.entity.ReportTypeEntity
import com.example.data.remote.dto.ReportTypeDto
import com.example.domain.model.ReportType
import com.example.domain.provider.AbstractResourceProvider
import com.example.util.mapper.DataMapper

class ReportTypeUiMapper : DataMapper<ReportTypeEntity, ReportType> {
    override fun map(input: ReportTypeEntity): ReportType {
        return ReportType(id = input.id, title = input.type, isSelected = false)
    }
}

class ReportTypeDtoMapper(
    private val resourceProvider: AbstractResourceProvider<String>
) : DataMapper<ReportTypeDto, ReportTypeEntity> {
    override fun map(input: ReportTypeDto): ReportTypeEntity {
        return ReportTypeEntity(
            id = input.id,
            type = resourceProvider.getResourceByName(input.type)
        )
    }
}
