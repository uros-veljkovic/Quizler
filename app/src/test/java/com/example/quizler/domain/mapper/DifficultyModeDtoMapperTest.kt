package com.example.quizler.domain.mapper

import com.example.data.remote.dto.DifficultyModeDto
import com.example.data.remote.dto.DifficultyModesDto
import com.example.domain.mapper.DifficultyModeDtoMapper
import org.junit.Assert.assertEquals
import org.junit.Test

class DifficultyModeDtoMapperTest {

    @Test
    fun `test map`() {
        // Given
        val difficultyModeDto = DifficultyModeDto(
            id = "1",
            parentModeId = "parent1",
            name = "easy",
            numberOfHints = 3,
            numberOfQuestions = 20,
            timePerQuestion = 15
        )

        val difficultyModesDto = DifficultyModesDto(
            id = "parent1",
            name = "parent",
            submodes = listOf(difficultyModeDto)
        )

        // When
        val mapper = DifficultyModeDtoMapper()
        val result = mapper.map(difficultyModesDto)

        // Then
        assertEquals(1, result.size)
        assertEquals(difficultyModeDto.id, result[0].id)
        assertEquals(difficultyModeDto.name, result[0].name)
        assertEquals(difficultyModeDto.numberOfHints, result[0].numberOfHints)
        assertEquals(difficultyModeDto.numberOfQuestions, result[0].numberOfQuestions)
        assertEquals(difficultyModeDto.timePerQuestion, result[0].timePerQuestion)
    }
}
