package com.example.quizler.domain.data.remote

import com.example.quizler.data.remote.dto.AnswerRecordDto
import com.example.quizler.data.remote.dto.CategoryModesDto
import com.example.quizler.data.remote.dto.CreateNewQuestionDto
import com.example.quizler.data.remote.dto.DifficultyModesDto
import com.example.quizler.data.remote.dto.LengthModesDto
import com.example.quizler.data.remote.dto.QuestionDto
import com.example.quizler.data.remote.dto.ReportQuestionDto
import com.example.quizler.data.remote.dto.ReportTypeDto
import com.example.quizler.data.remote.dto.ResultRecordDto
import com.example.quizler.data.remote.dto.ScoreDto
import com.example.quizler.domain.data.RepositoryResponse

interface IQuizRemoteRepository {

    suspend fun getCategoryModes(): RepositoryResponse<CategoryModesDto>
    suspend fun getLengthModes(): RepositoryResponse<LengthModesDto>
    suspend fun getDifficultyModes(): RepositoryResponse<DifficultyModesDto>
    suspend fun getQuestions(): RepositoryResponse<List<QuestionDto>>
    suspend fun getReportTypes(): RepositoryResponse<List<ReportTypeDto>>
    suspend fun getScores(): RepositoryResponse<List<ScoreDto>>
    suspend fun record(answerRecordDto: AnswerRecordDto): RepositoryResponse<Unit>
    suspend fun record(resultRecordDto: ResultRecordDto): RepositoryResponse<Unit>
    suspend fun report(questionId: String): RepositoryResponse<Unit>
    suspend fun report(dto: ReportQuestionDto): RepositoryResponse<Unit>
    suspend fun create(question: CreateNewQuestionDto): RepositoryResponse<Unit>
}
