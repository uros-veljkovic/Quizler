package com.example.quizler.data.local.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.quizler.data.local.db.BaseDao
import com.example.quizler.data.local.entity.DifficultyModeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DifficultyModeDao : BaseDao<DifficultyModeEntity> {

    @Query("SELECT * FROM difficulty")
    fun readlAll(): Flow<List<DifficultyModeEntity>>

    @Query("DELETE FROM difficulty")
    suspend fun deleteAll()
}
