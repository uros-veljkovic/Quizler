package com.example.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.data.model.Difficulty
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class QuestionWithAnswersDaoTest {

    private lateinit var db: com.example.data.local.db.dao.QuizlerDatabase
    private lateinit var questionDao: com.example.data.local.db.dao.QuestionDao
    private lateinit var answerDao: com.example.data.local.db.dao.AnswerDao
    private lateinit var sut: com.example.data.local.db.dao.QuestionWithAnswersDao

    private val questionEntity = com.example.data.local.entity.QuestionEntity(
        id = "name",
        text = QUESTION_ID_1,
        difficulty = Difficulty.Easy,
        categoryId = "categoryId",
        isApproved = true
    )
    private val answers = listOf(
        com.example.data.local.entity.AnswerEntity(
            answerId = "1",
            questionId = QUESTION_ID_1,
            text = "Answer 1",
            isCorrect = true
        ),
        com.example.data.local.entity.AnswerEntity(
            answerId = "2",
            questionId = QUESTION_ID_1,
            text = "Answer 2",
            isCorrect = false
        ),
        com.example.data.local.entity.AnswerEntity(
            answerId = "3",
            questionId = QUESTION_ID_2,
            text = "Answer 3",
            isCorrect = false
        )
    )

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, com.example.data.local.db.dao.QuizlerDatabase::class.java).build()
        questionDao = db.daoQuestion()
        answerDao = db.daoAnswer()
        sut = db.daoQuestionsWithAnswers()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun givenEmptyDatabse_whenInsertedQuestionAndAnswer_thenQuestionWillBeJoinedWithAppropriateAnswersById() =
        runBlocking {
            assertDoesNotThrow {
                questionDao.insert(questionEntity)
                answers.forEach {
                    answerDao.insert(it)
                }
            }
            val entries = sut.readAll().first()

            val isQuestionPresent = entries.count { it.question.id == questionEntity.id } == 1
            val has3Answers = entries.first().answers.count() == answers.count { it.answerId == questionEntity.id }
            assertTrue(isQuestionPresent && has3Answers)
        }

    companion object {
        const val QUESTION_ID_1 = "ID_1"
        const val QUESTION_ID_2 = "ID_2"
    }
}
