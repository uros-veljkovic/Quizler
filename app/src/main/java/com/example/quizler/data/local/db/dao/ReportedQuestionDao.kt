package com.example.quizler.data.local.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.quizler.data.local.db.BaseDao
import com.example.quizler.data.local.entity.ReportedQuestionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReportedQuestionDao : BaseDao<ReportedQuestionEntity> {

    @Query("SELECT * FROM reported_question")
    fun readAll(): Flow<List<ReportedQuestionEntity>>

    @Query("DELETE FROM reported_question")
    suspend fun deleteAll()
}
