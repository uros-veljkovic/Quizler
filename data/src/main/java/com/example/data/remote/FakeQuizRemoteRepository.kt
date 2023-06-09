package com.example.data.remote

import com.example.data.remote.dto.AnswerRecordDto
import com.example.data.remote.dto.CategoryModesDto
import com.example.data.remote.dto.CreateNewQuestionDto
import com.example.data.remote.dto.DifficultyModesDto
import com.example.data.remote.dto.LengthModesDto
import com.example.data.remote.dto.QuestionDto
import com.example.data.remote.dto.ReportQuestionDto
import com.example.data.remote.dto.ReportTypeDto
import com.example.data.remote.dto.ResultRecordDto
import com.example.data.remote.dto.ScoreDto
import com.example.util.data.RepositoryResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.FileReader

class FakeQuizRemoteRepository : IQuizRemoteRepository {
    override suspend fun getCategoryModes(): RepositoryResponse<CategoryModesDto> {
        val json = withContext(Dispatchers.IO) {
            FileReader("category_fakes.json").readText()
        }
        return RepositoryResponse.Success(
            Gson().fromJson(json, CategoryModesDto::class.java)
        )
    }

    override suspend fun getLengthModes(): RepositoryResponse<LengthModesDto> {
        val json = withContext(Dispatchers.IO) {
            FileReader("length_fakes.json").readText()
        }
        return RepositoryResponse.Success(
            Gson().fromJson(json, LengthModesDto::class.java)
        )
    }

    override suspend fun getDifficultyModes(): RepositoryResponse<DifficultyModesDto> {
        val json = withContext(Dispatchers.IO) {
            FileReader("difficulty_fakes.json").readText()
        }
        return RepositoryResponse.Success(
            Gson().fromJson(json, DifficultyModesDto::class.java)
        )
    }

    override suspend fun getQuestions(): RepositoryResponse<List<QuestionDto>> {
        return RepositoryResponse.Success(emptyList())
    }

    override suspend fun getReportTypes(): RepositoryResponse<List<ReportTypeDto>> {
        return RepositoryResponse.Success(emptyList())
    }

    override suspend fun getScores(): RepositoryResponse<List<ScoreDto>> {
        val json = withContext(Dispatchers.IO) {
            FileReader("score_fakes.json").readText()
        }
        return RepositoryResponse.Success(
            Gson().fromJson(json, object : TypeToken<List<ScoreDto>>() {}.type)
        )
    }

    override suspend fun record(answerRecordDto: AnswerRecordDto): RepositoryResponse<Unit> {
        return RepositoryResponse.Success(Unit)
    }

    override suspend fun record(resultRecordDto: ResultRecordDto): RepositoryResponse<Unit> {
        return RepositoryResponse.Success(Unit)
    }

    override suspend fun report(questionId: String): RepositoryResponse<Unit> {
        return RepositoryResponse.Success(Unit)
    }

    override suspend fun report(dto: ReportQuestionDto): RepositoryResponse<Unit> {
        return RepositoryResponse.Success(Unit)
    }

    override suspend fun create(question: CreateNewQuestionDto): RepositoryResponse<Unit> {
        return RepositoryResponse.Success(Unit)
    }
}