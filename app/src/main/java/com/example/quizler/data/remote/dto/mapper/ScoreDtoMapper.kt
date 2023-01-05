package com.example.quizler.data.remote.dto.mapper

import com.example.quizler.data.local.entity.ScoreEntity
import com.example.quizler.data.remote.dto.ScoreDto
import com.example.quizler.util.mapper.DataMapper

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
