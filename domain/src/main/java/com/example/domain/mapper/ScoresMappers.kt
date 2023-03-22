package com.example.domain.mapper

import com.example.data.local.entity.ScoreEntity
import com.example.data.remote.dto.ScoreDto
import com.example.domain.model.ScoreModel
import com.example.util.mapper.DataMapper

class ScoresEntityMapper : DataMapper<ScoreEntity, ScoreModel> {
    override fun map(input: ScoreEntity): ScoreModel {
        return ScoreModel(
            id = input._id,
            username = input.username,
            mode = input.mode,
            score = input.score,
            ranking = input.ranking
        )
    }
}

class ScoreDtoMapper : DataMapper<Pair<ScoreDto, Int>, ScoreEntity> {
    override fun map(input: Pair<ScoreDto, Int>): ScoreEntity {
        return ScoreEntity(
            _id = input.first.id,
            username = input.first.username,
            score = input.first.score,
            mode = input.first.mode,
            ranking = input.second
        )
    }
}
