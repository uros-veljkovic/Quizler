package com.example.quizler.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.quizler.data.local.db.dao.QuizlerDatabase
import com.example.quizler.data.local.db.dao.ReportTypesDao
import com.example.quizler.data.local.entity.ReportTypeEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ReportedTypesDaoTest {

    private lateinit var db: QuizlerDatabase
    private lateinit var sut: ReportTypesDao
    private val entity = ReportTypeEntity(
        id = "id",
        type = "type"
    )

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, QuizlerDatabase::class.java).build()
        sut = db.daoReportTypes()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun givenEmptyDatabse_whenTwoSameEntitiesInserted_thenTheAppWillNotCrash() = runBlocking {
        assertDoesNotThrow {
            sut.insert(entity)
            sut.insert(entity)
        }
        val list = sut.readAll().first()
        assertTrue(list.count { it.id == entity.id } == 1)
    }

    @Test
    fun givenTableWithSingleEntity_whenDeleted_thenTableIsEmpty() = runBlocking {
        sut.insert(entity)
        sut.delete(entity)
        val list = sut.readAll().first()
        assertTrue(list.isEmpty())
    }

    @Test
    fun givenEmptyTable_whenDeletingNonExistingEntity_thenTheAppWillNotCrash() = runBlocking {
        assertDoesNotThrow {
            sut.delete(entity)
        }
    }
}
