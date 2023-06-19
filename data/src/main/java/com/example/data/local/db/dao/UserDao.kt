package com.example.data.local.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.data.local.UserEntity
import com.example.data.local.db.BaseDao
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao : BaseDao<UserEntity> {

    @Query("SELECT * FROM user")
    fun readAll(): Flow<List<UserEntity>>

    @Query("DELETE FROM user")
    suspend fun deleteAll()
}
