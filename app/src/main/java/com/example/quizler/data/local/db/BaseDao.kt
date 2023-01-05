package com.example.quizler.data.local.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Transaction

@Dao
interface BaseDao<T> {

    @Transaction
    @Insert
    suspend fun insert(data: T)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    suspend fun insert(data: List<T>)

    @Delete
    suspend fun delete(data: T)
}
