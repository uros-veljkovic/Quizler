package com.example.quizler.ui.screen.score

import com.example.domain.model.ScoreModel
import com.example.quizler.model.Score
import com.example.util.mapper.DataMapper

class ScoresUiMapper : DataMapper<ScoreModel, Score> {
    override fun map(input: ScoreModel): Score {
        return Score(
            id = input.id,
            username = input.username,
            mode = input.mode,
            score = input.score,
            ranking = input.ranking
        )
    }
}
