package com.example.quizler.data.local.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.quizler.data.local.db.BaseDao
import com.example.quizler.data.local.entity.InvalidQuestionReportEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReportedQuestionDao : BaseDao<InvalidQuestionReportEntity> {

    @Query("SELECT * FROM reported_question")
    fun readAll(): Flow<List<InvalidQuestionReportEntity>>

    @Query("DELETE FROM reported_question")
    suspend fun deleteAll()
}
