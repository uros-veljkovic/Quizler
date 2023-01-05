package com.example.quizler.data.local.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.quizler.data.local.db.BaseDao
import com.example.quizler.data.local.entity.QuestionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionDao : BaseDao<QuestionEntity> {

    @Transaction
    @Query("SELECT * FROM question")
    fun readlAll(): Flow<List<QuestionEntity>>

    @Query("DELETE FROM question")
    suspend fun deleteAll()
}
