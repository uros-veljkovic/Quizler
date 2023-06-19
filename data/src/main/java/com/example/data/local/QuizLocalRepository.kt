package com.example.data.local

import androidx.room.withTransaction
import com.example.data.local.db.dao.QuizlerDatabase
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
import kotlinx.coroutines.flow.map

class QuizLocalRepository(
    private val db: QuizlerDatabase,
) : IQuizLocalRepository {
    override fun readUser(): Flow<UserEntity?> {
        return db.daoUser().readAll().map { it.firstOrNull() }
    }

    override fun readCategoriesModes(): Flow<List<CategoryModeEntity>> {
        return db.daoCategory().readAll()
    }

    override fun readDifficultyModes(): Flow<List<DifficultyModeEntity>> {
        return db.daoDifficulty().readlAll()
    }

    override fun readLengthModes(): Flow<List<LengthModeEntity>> {
        return db.daoLength().readAll()
    }

    override fun readQuestionsWithAnswers(): Flow<List<QuestionWithAnswersEntity>> {
        return db.daoQuestionsWithAnswers().readAll()
    }

    override fun readAnswerRecords(): Flow<List<AnswerRecordEntity>> {
        return db.daoAnswerRecord().readAll()
    }

    override fun readResultRecords(): Flow<List<ResultRecordEntity>> {
        return db.daoResultRecord().readAll()
    }

    override fun readScores(): Flow<List<ScoreEntity>> {
        return db.daoScores().readAll()
    }

    override fun readReportedQuestions(): Flow<List<InvalidQuestionReportEntity>> {
        return db.daoReportedQuestion().readAll()
    }

    override fun readReportTypes(): Flow<List<ReportTypeEntity>> {
        return db.daoReportTypes().readAll()
    }

    override suspend fun insertUser(data: UserEntity) {
        return db.daoUser().insert(data)
    }

    override suspend fun insertLengthModes(data: List<LengthModeEntity>) {
        db.daoLength().insert(data)
    }

    override suspend fun insertDifficultyModes(data: List<DifficultyModeEntity>) {
        db.daoDifficulty().insert(data)
    }

    override suspend fun insertCategoriesModes(data: List<CategoryModeEntity>) {
        db.daoCategory().insert(data)
    }

    override suspend fun insertQuestionsWithAnswers(data: List<QuestionWithAnswersEntity>) {
        db.withTransaction {
            db.daoQuestion().insert(data.map { it.question })
            db.daoAnswer().insert(data.flatMap { it.answers })
        }
    }

    override suspend fun insertAnswerRecord(data: AnswerRecordEntity) {
        db.daoAnswerRecord().insert(data)
    }

    override suspend fun insertResultRecord(data: ResultRecordEntity) {
        db.daoResultRecord().insert(data)
    }

    override suspend fun insertScores(data: List<ScoreEntity>) {
        with(db.daoScores()) {
            deleteAll()
            insert(data)
        }
    }

    override suspend fun insertReportedQuestion(data: InvalidQuestionReportEntity) {
        db.daoReportedQuestion().insert(data)
    }

    override suspend fun insertReportTypes(data: List<ReportTypeEntity>) {
        db.daoReportTypes().insert(data)
    }

    override suspend fun deleteUser() {
        db.daoUser().deleteAll()
    }

    override suspend fun deleteAllAnswerRecords() {
        db.daoAnswerRecord().deleteAll()
    }

    override suspend fun deleteAllResultRecords() {
        db.daoResultRecord().deleteAll()
    }

    override suspend fun deleteAllReportedQuestions() {
        db.daoReportedQuestion().deleteAll()
    }
}
