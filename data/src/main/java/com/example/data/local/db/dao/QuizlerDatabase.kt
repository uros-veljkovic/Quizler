package com.example.data.local.db.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.local.UserEntity
import com.example.data.local.entity.AnswerEntity
import com.example.data.local.entity.AnswerRecordEntity
import com.example.data.local.entity.CategoryModeEntity
import com.example.data.local.entity.DifficultyModeEntity
import com.example.data.local.entity.InvalidQuestionReportEntity
import com.example.data.local.entity.LengthModeEntity
import com.example.data.local.entity.QuestionEntity
import com.example.data.local.entity.ReportTypeEntity
import com.example.data.local.entity.ResultRecordEntity
import com.example.data.local.entity.ScoreEntity

@Database(
    entities = [
        UserEntity::class,
        CategoryModeEntity::class,
        LengthModeEntity::class,
        DifficultyModeEntity::class,
        QuestionEntity::class,
        AnswerEntity::class,
        AnswerRecordEntity::class,
        ResultRecordEntity::class,
        ScoreEntity::class,
        InvalidQuestionReportEntity::class,
        ReportTypeEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class QuizlerDatabase : RoomDatabase() {
    abstract fun daoUser(): UserDao
    abstract fun daoCategory(): CategoryModeDao
    abstract fun daoLength(): LengthModeDao
    abstract fun daoDifficulty(): DifficultyModeDao
    abstract fun daoQuestion(): QuestionDao
    abstract fun daoAnswer(): AnswerDao
    abstract fun daoQuestionsWithAnswers(): QuestionWithAnswersDao
    abstract fun daoAnswerRecord(): AnswerRecordDao
    abstract fun daoResultRecord(): ResultRecordDao
    abstract fun daoScores(): ScoresDao
    abstract fun daoReportedQuestion(): ReportedQuestionDao
    abstract fun daoReportTypes(): ReportTypesDao
}
