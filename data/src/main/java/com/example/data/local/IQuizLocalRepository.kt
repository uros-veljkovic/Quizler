package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.local.entity.AnswerRecordEntity
import com.example.data.local.entity.CategoryModeEntity
import com.example.data.local.entity.DifficultyModeEntity
import com.example.data.local.entity.InvalidQuestionReportEntity
import com.example.data.local.entity.LengthModeEntity
import com.example.data.local.entity.QuestionWithAnswersEntity
import com.example.data.local.entity.ReportTypeEntity
import com.example.data.local.entity.ResultRecordEntity
import com.example.data.local.entity.ScoreEntity
import kotlinx.coroutines.flow.Flow

interface IQuizLocalRepository {
    fun readUser(): Flow<UserEntity?>
    fun readCategoriesModes(): Flow<List<CategoryModeEntity>>
    fun readDifficultyModes(): Flow<List<DifficultyModeEntity>>
    fun readLengthModes(): Flow<List<LengthModeEntity>>
    fun readQuestionsWithAnswers(): Flow<List<QuestionWithAnswersEntity>>
    fun readAnswerRecords(): Flow<List<AnswerRecordEntity>>
    fun readResultRecords(): Flow<List<ResultRecordEntity>>
    fun readScores(): Flow<List<ScoreEntity>>
    fun readReportedQuestions(): Flow<List<InvalidQuestionReportEntity>>
    fun readReportTypes(): Flow<List<ReportTypeEntity>>

    suspend fun insertUser(data: UserEntity)
    suspend fun insertLengthModes(data: List<LengthModeEntity>)
    suspend fun insertDifficultyModes(data: List<DifficultyModeEntity>)
    suspend fun insertCategoriesModes(data: List<CategoryModeEntity>)
    suspend fun insertQuestionsWithAnswers(data: List<QuestionWithAnswersEntity>)
    suspend fun insertAnswerRecord(data: AnswerRecordEntity)
    suspend fun insertResultRecord(data: ResultRecordEntity)
    suspend fun insertScores(data: List<ScoreEntity>)
    suspend fun insertReportedQuestion(data: InvalidQuestionReportEntity)
    suspend fun insertReportTypes(data: List<ReportTypeEntity>)

    suspend fun deleteUser()
    suspend fun deleteAllAnswerRecords()
    suspend fun deleteAllResultRecords()
    suspend fun deleteAllReportedQuestions()
}

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey val userId: String,
    val username: String?,
    val avatarName: String?,
    val profileImageUrl: String?,
)
