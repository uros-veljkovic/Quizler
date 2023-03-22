package com.example.domain.mapper

import com.example.data.remote.dto.CreateNewQuestionAnswerDto
import com.example.data.remote.dto.CreateNewQuestionDto
import com.example.domain.model.CreateNewQuestionBundle
import com.example.util.mapper.DataMapper

class CreateNewQuestionMappers : DataMapper<CreateNewQuestionBundle, CreateNewQuestionDto> {
    override fun map(input: CreateNewQuestionBundle): CreateNewQuestionDto {
        return CreateNewQuestionDto(
            text = input.question,
            categoryId = input.categoryId,
            answers = input.answers.map {
                CreateNewQuestionAnswerDto(
                    text = it.text.trim(),
                    isCorrect = it.isCorrect
                )
            }
        )
    }
}
