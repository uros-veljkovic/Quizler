package com.example.data.local.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.data.local.db.BaseDao
import com.example.data.local.entity.ResultRecordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ResultRecordDao : BaseDao<ResultRecordEntity> {

    @Transaction
    @Query("SELECT * FROM result_records")
    fun readAll(): Flow<List<ResultRecordEntity>>

    @Query("DELETE FROM result_records")
    fun deleteAll()
}
