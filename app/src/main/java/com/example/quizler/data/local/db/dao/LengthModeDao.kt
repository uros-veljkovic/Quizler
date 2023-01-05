package com.example.quizler.data.local.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.quizler.data.local.db.BaseDao
import com.example.quizler.data.local.entity.LengthModeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LengthModeDao : BaseDao<LengthModeEntity> {

    @Query("SELECT * FROM length")
    fun readAll(): Flow<List<LengthModeEntity>>

    @Query("DELETE FROM length")
    suspend fun deleteAll()
}
