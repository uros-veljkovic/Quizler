package com.example.quizler.domain.data.local

import com.example.quizler.data.local.entity.AnswerRecordEntity
import com.example.quizler.data.local.entity.CategoryModeEntity
import com.example.quizler.data.local.entity.DifficultyModeEntity
import com.example.quizler.data.local.entity.LengthModeEntity
import com.example.quizler.data.local.entity.QuestionWithAnswersEntity
import com.example.quizler.data.local.entity.ReportTypeEntity
import com.example.quizler.data.local.entity.InvalidQuestionReportEntity
import com.example.quizler.data.local.entity.ResultRecordEntity
import com.example.quizler.data.local.entity.ScoreEntity
import kotlinx.coroutines.flow.Flow

interface IQuizLocalRepository {
    fun readCategoriesModes(): Flow<List<CategoryModeEntity>>
    fun readDifficultyModes(): Flow<List<DifficultyModeEntity>>
    fun readLengthModes(): Flow<List<LengthModeEntity>>
    fun readQuestionsWithAnswers(): Flow<List<QuestionWithAnswersEntity>>
    fun readAnswerRecords(): Flow<List<AnswerRecordEntity>>
    fun readResultRecords(): Flow<List<ResultRecordEntity>>
    fun readScores(): Flow<List<ScoreEntity>>
    fun readReportedQuestions(): Flow<List<InvalidQuestionReportEntity>>
    fun readReportTypes(): Flow<List<ReportTypeEntity>>

    suspend fun insertLengthModes(data: List<LengthModeEntity>)
    suspend fun insertDifficultyModes(data: List<DifficultyModeEntity>)
    suspend fun insertCategoriesModes(data: List<CategoryModeEntity>)
    suspend fun insertQuestionsWithAnswers(data: List<QuestionWithAnswersEntity>)
    suspend fun insertAnswerRecord(data: AnswerRecordEntity)
    suspend fun insertResultRecord(data: ResultRecordEntity)
    suspend fun insertScores(data: List<ScoreEntity>)
    suspend fun insertReportedQuestion(data: InvalidQuestionReportEntity)
    suspend fun insertReportTypes(data: List<ReportTypeEntity>)

    suspend fun deleteAllAnswerRecords()
    suspend fun deleteAllResultRecords()
    suspend fun deleteAllReportedQuestions()
}
