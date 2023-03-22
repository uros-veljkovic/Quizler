package com.example.domain.mapper

import com.example.data.local.entity.QuestionEntity
import com.example.data.remote.dto.QuestionDto
import com.example.domain.model.Difficulty
import com.example.domain.model.Question
import com.example.util.mapper.DataMapper

class QuestionDtoMappers : DataMapper<QuestionDto, QuestionEntity> {
    override fun map(input: QuestionDto): QuestionEntity {
        val difficulty = when (input.countAnsweredCorrect.toDouble() / input.countAnsweredWrong.toDouble()) {
            in MIN_PERCENTAGE_FOR_HARD..MAX_PERCENTAGE_FOR_HARD -> Difficulty.Hard
            in MIN_PERCENTAGE_FOR_MEDIUM..MAX_PERCENTAGE_FOR_MEDIUM -> Difficulty.Medium
            else -> Difficulty.Easy
        }

        return QuestionEntity(
            id = input.id,
            difficulty = difficulty.name,
            text = input.text,
            categoryId = input.categoryId,
            isApproved = input.isApproved
        )
    }

    companion object {
        private const val MIN_PERCENTAGE_FOR_HARD = 0.0
        private const val MAX_PERCENTAGE_FOR_HARD = 0.33
        private const val MIN_PERCENTAGE_FOR_MEDIUM = 0.33
        private const val MAX_PERCENTAGE_FOR_MEDIUM = 0.66
    }
}

class QuestionDomainMapper : DataMapper<QuestionEntity, Question> {
    override fun map(input: QuestionEntity): Question {
        return Question(
            id = input.id,
            text = input.text,
            categoryId = input.categoryId,
        )
    }
}
