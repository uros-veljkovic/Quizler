package com.example.quizler.data.local.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.quizler.data.local.db.BaseDao
import com.example.quizler.data.local.entity.AnswerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AnswerDao : BaseDao<AnswerEntity> {

    @Query("SELECT * FROM answer")
    fun readAll(): Flow<List<AnswerEntity>>

    @Query("DELETE FROM answer")
    suspend fun deleteAll()
}
