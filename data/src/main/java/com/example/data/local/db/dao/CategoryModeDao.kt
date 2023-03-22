package com.example.data.local.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.data.local.db.BaseDao
import com.example.data.local.entity.CategoryModeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryModeDao : BaseDao<CategoryModeEntity> {

    @Query("SELECT * FROM category")
    fun readAll(): Flow<List<CategoryModeEntity>>

    @Query("DELETE FROM category")
    suspend fun deleteAll()
}
