package com.example.quizler.data.local.db.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.quizler.data.local.entity.AnswerEntity
import com.example.quizler.data.local.entity.AnswerRecordEntity
import com.example.quizler.data.local.entity.CategoryModeEntity
import com.example.quizler.data.local.entity.DifficultyModeEntity
import com.example.quizler.data.local.entity.LengthModeEntity
import com.example.quizler.data.local.entity.QuestionEntity
import com.example.quizler.data.local.entity.ReportTypeEntity
import com.example.quizler.data.local.entity.ReportedQuestionEntity
import com.example.quizler.data.local.entity.ResultRecordEntity
import com.example.quizler.data.local.entity.ScoreEntity

@Database(
    entities = [
        CategoryModeEntity::class,
        LengthModeEntity::class,
        DifficultyModeEntity::class,
        QuestionEntity::class,
        AnswerEntity::class,
        AnswerRecordEntity::class,
        ResultRecordEntity::class,
        ScoreEntity::class,
        ReportedQuestionEntity::class,
        ReportTypeEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class QuizModeDatabase : RoomDatabase() {
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
