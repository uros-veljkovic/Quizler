package com.example.data.local.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.data.local.entity.QuestionWithAnswersEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionWithAnswersDao {

    @Transaction
    @Query("SELECT * FROM question")
    fun readAll(): Flow<List<QuestionWithAnswersEntity>>
}
