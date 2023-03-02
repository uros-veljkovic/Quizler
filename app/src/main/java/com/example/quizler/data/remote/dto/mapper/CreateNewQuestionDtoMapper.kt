package com.example.quizler.data.remote.dto.mapper

import com.example.quizler.data.remote.dto.CreateNewQuestionDto
import com.example.quizler.data.remote.dto.CreateNewQuestionAnswerDto
import com.example.quizler.ui.screen.newquestion.CreateNewQuestionScreenState
import com.example.quizler.util.mapper.DataMapper
import java.lang.Exception

class CreateNewQuestionDtoMapper : DataMapper<CreateNewQuestionScreenState, CreateNewQuestionDto> {
    override fun map(input: CreateNewQuestionScreenState): CreateNewQuestionDto {
        return CreateNewQuestionDto(
            text = input.question.text,
            categoryId = input.chosenCategory?.itemId ?: throw Exception("No category has been chosen !"),
            answers = input.answers.map {
                CreateNewQuestionAnswerDto(
                    text = it.text,
                    isCorrect = it.type == input.chosenCorrectAnswer
                )
            }
        )
    }
}