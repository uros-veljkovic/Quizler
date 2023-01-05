package com.example.quizler.data.remote.service.quizmode

import com.example.quizler.data.remote.dto.AnswerRecordDto
import com.example.quizler.data.remote.dto.CategoryModesDto
import com.example.quizler.data.remote.dto.DifficultyModesDto
import com.example.quizler.data.remote.dto.LengthModesDto
import com.example.quizler.data.remote.dto.QuestionDto
import com.example.quizler.data.remote.dto.ResultRecordDto
import com.example.quizler.data.remote.dto.ScoreDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface QuizService {

    @GET("modes/category")
    suspend fun getCategoryModes(): Response<CategoryModesDto>

    @GET("modes/length")
    suspend fun getLengthModes(): Response<LengthModesDto>

    @GET("modes/difficulty")
    suspend fun getDifficultyModes(): Response<DifficultyModesDto>

    @GET("questions")
    suspend fun getQuestions(
        @Query("is_approved") approvedQuestions: Boolean,
    ): Response<List<QuestionDto>>

    @GET("scores")
    suspend fun getScores(): Response<List<ScoreDto>>

    @POST("questions/answered")
    suspend fun record(@Body answerRecordDto: AnswerRecordDto): Response<Unit>

    @POST("scores")
    suspend fun record(@Body resultRecordDto: ResultRecordDto): Response<Unit>

    @POST("questions/report")
    suspend fun report(@Query("id") questionId: String): Response<Unit>
}
