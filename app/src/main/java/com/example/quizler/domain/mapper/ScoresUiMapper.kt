package com.example.quizler.domain.mapper

import com.example.quizler.data.local.entity.ScoreEntity
import com.example.quizler.ui.model.Score
import com.example.quizler.util.mapper.DataMapper

class ScoresUiMapper : DataMapper<ScoreEntity, Score> {
    override fun map(input: ScoreEntity): Score {
        return Score(
            id = input._id,
            username = input.username,
            mode = input.mode,
            score = input.score,
            ranking = input.ranking
        )
    }
}
