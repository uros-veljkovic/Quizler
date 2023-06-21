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
import com.example.data.remote.dto.UpdateUserProfileDto
import com.example.data.remote.dto.user.UserDto
import com.example.util.data.RepositoryResponse

interface IQuizRemoteRepository {

    suspend fun signIn(): RepositoryResponse<UserDto>
    suspend fun getCurrentUser(): RepositoryResponse<UserDto>
    suspend fun getUser(id: String): RepositoryResponse<UserDto>
    suspend fun getCategoryModes(): RepositoryResponse<CategoryModesDto>
    suspend fun getLengthModes(): RepositoryResponse<LengthModesDto>
    suspend fun getDifficultyModes(): RepositoryResponse<DifficultyModesDto>
    suspend fun getQuestions(): RepositoryResponse<List<QuestionDto>>
    suspend fun getReportTypes(): RepositoryResponse<List<ReportTypeDto>>
    suspend fun getScores(): RepositoryResponse<List<ScoreDto>>
    suspend fun updateCurrentUser(dto: UpdateUserProfileDto): RepositoryResponse<UserDto>
    suspend fun record(answerRecordDto: AnswerRecordDto): RepositoryResponse<Unit>
    suspend fun record(resultRecordDto: ResultRecordDto): RepositoryResponse<Unit>
    suspend fun report(questionId: String): RepositoryResponse<Unit>
    suspend fun report(dto: ReportQuestionDto): RepositoryResponse<Unit>
    suspend fun create(question: CreateNewQuestionDto): RepositoryResponse<Unit>
}
