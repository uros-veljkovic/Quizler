package com.example.data.local.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.data.local.db.BaseDao
import com.example.data.local.entity.ScoreEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ScoresDao : BaseDao<ScoreEntity> {

    @Transaction
    @Query("SELECT * FROM score ORDER BY score DESC")
    fun readAll(): Flow<List<ScoreEntity>>

    @Query("DELETE FROM score")
    fun deleteAll()
}
