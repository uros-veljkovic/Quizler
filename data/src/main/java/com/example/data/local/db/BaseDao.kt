package com.example.data.local.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Transaction
import androidx.room.Upsert

@Dao
interface BaseDao<T> {

    @Transaction
    @Upsert
    suspend fun insert(data: T)

    @Transaction
    @Upsert
    @JvmSuppressWildcards
    suspend fun insert(data: List<T>)

    @Delete
    suspend fun delete(data: T)
}
