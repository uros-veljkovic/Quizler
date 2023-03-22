package com.example.domain.model.mode

class CategoryMode(
    id: String,
    name: String,
    numberOfHints: Int,
    numberOfQuestions: Int,
    timePerQuestion: Int
) : BaseMode(id, name, numberOfHints, numberOfQuestions, timePerQuestion)
