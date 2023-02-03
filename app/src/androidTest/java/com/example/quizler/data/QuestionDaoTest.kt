package com.example.quizler.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.quizler.data.local.db.dao.QuestionDao
import com.example.quizler.data.local.db.dao.QuizlerDatabase
import com.example.quizler.data.local.entity.QuestionEntity
import com.example.quizler.domain.model.Difficulty
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class QuestionDaoTest {

    private lateinit var db: QuizlerDatabase
    private lateinit var sut: QuestionDao
    private val entity = QuestionEntity(
        "id",
        "name",
        Difficulty.Easy,
        "categoryId",
        true
    )

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, QuizlerDatabase::class.java).build()
        sut = db.daoQuestion()
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
        val list = sut.readlAll().first()
        assertTrue(list.count { it.id == entity.id } == 1)
    }

    @Test
    fun givenTableWithSingleEntity_whenDeleted_thenTableIsEmpty() = runBlocking {
        sut.insert(entity)
        sut.delete(entity)
        val list = sut.readlAll().first()
        assertTrue(list.isEmpty())
    }

    @Test
    fun givenEmptyTable_whenDeletingNonExistingEntity_thenTheAppWillNotCrash() = runBlocking {
        assertDoesNotThrow {
            sut.delete(entity)
        }
    }

    @Test
    fun givenEmptyTable_whenEntityInsertedAndThenUpdated_thenTableWillBeUpdated1() = runBlocking {
        assertDoesNotThrow {
            sut.insert(entity.copy(difficulty = Difficulty.Easy))
            sut.insert(entity.copy(difficulty = Difficulty.Medium))
        }

        val list = sut.readlAll().first()
        assertTrue(list.count { it.id == entity.id && it.difficulty == Difficulty.Easy } == 0)
        assertTrue(list.count { it.id == entity.id && it.difficulty == Difficulty.Medium } == 1)
    }

    @Test
    fun givenEmptyTable_whenEntityInsertedAndThenUpdated_thenTableWillBeUpdated2() = runBlocking {
        assertDoesNotThrow {
            sut.insert(entity.copy(categoryId = CATEGORY_1))
            sut.insert(entity.copy(categoryId = CATEGORY_2))
        }

        val list = sut.readlAll().first()
        assertTrue(list.count { it.id == entity.id && it.categoryId == CATEGORY_1 } == 0)
        assertTrue(list.count { it.id == entity.id && it.categoryId == CATEGORY_2 } == 1)
    }

    companion object {
        const val CATEGORY_1 = "category1"
        const val CATEGORY_2 = "category2"
    }
}
