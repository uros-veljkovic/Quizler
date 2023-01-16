package com.example.quizler.data.local.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.quizler.data.local.db.BaseDao
import com.example.quizler.data.local.entity.ReportTypeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReportTypesDao : BaseDao<ReportTypeEntity> {

    @Query("SELECT * FROM report_type")
    fun readAll(): Flow<List<ReportTypeEntity>>

    @Query("DELETE FROM reported_question")
    suspend fun deleteAll()
}
