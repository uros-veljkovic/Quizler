package com.example.data.local.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.data.local.db.BaseDao
import com.example.data.local.entity.AnswerRecordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AnswerRecordDao : BaseDao<AnswerRecordEntity> {

    @Transaction
    @Query("SELECT * FROM answer_records")
    fun readAll(): Flow<List<AnswerRecordEntity>>

    @Query("DELETE FROM answer_records")
    fun deleteAll()
}
