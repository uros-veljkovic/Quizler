package com.example.domain.model.mode

class DifficultyMode(
    id: String,
    name: String,
    numberOfHints: Int,
    numberOfQuestions: Int,
    timePerQuestion: Int
) : BaseMode(id, name, numberOfHints, numberOfQuestions, timePerQuestion)
