package com.example.data.local.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.data.local.db.BaseDao
import com.example.data.local.entity.UserProfileEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao : BaseDao<UserProfileEntity> {

    @Query("SELECT * FROM user")
    fun readAll(): Flow<List<UserProfileEntity>>

    @Query("DELETE FROM user")
    suspend fun deleteAll()
}
