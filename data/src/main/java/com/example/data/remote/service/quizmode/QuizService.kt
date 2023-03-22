package com.example.data.remote.service.quizmode

import androidx.annotation.Keep
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
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

@Keep
interface QuizService {

    @GET("modes/category")
    suspend fun getCategoryModes(): Response<CategoryModesDto>

    @GET("modes/length")
    suspend fun getLengthModes(): Response<LengthModesDto>

    @GET("modes/difficulty")
    suspend fun getDifficultyModes(): Response<DifficultyModesDto>

    @GET("questions")
    suspend fun getQuestions(): Response<List<QuestionDto>>

    @GET("scores")
    suspend fun getScores(): Response<List<ScoreDto>>

    @POST("questions/answered")
    suspend fun record(@Body answerRecordDto: AnswerRecordDto): Response<Unit>

    @POST("scores")
    suspend fun record(@Body resultRecordDto: ResultRecordDto): Response<Unit>

    @POST("questions/report")
    suspend fun report(@Query("id") questionId: String): Response<Unit>

    @GET("report-types")
    suspend fun getReportTypes(): Response<List<ReportTypeDto>>

    @POST("invalid-question")
    suspend fun reportQuestion(@Body dto: ReportQuestionDto): Response<Unit>

    @POST("questions/create")
    suspend fun create(@Body dto: CreateNewQuestionDto): Response<Unit>
}
