package com.example.quizler.ui.screen.quiz.mapper

import com.example.quizler.data.local.entity.QuestionEntity
import com.example.quizler.data.local.entity.QuestionWithAnswersEntity
import com.example.quizler.domain.model.Answer
import com.example.quizler.domain.model.AnswerType
import com.example.quizler.domain.model.Question
import com.example.quizler.ui.screen.quiz.QuestionBundle
import com.example.quizler.ui.screen.quiz.bundle.AnswerEntityBundle
import com.example.quizler.util.mapper.DataMapper
import javax.inject.Inject

class QuestionBundleMapper @Inject constructor(
    private val questionMapper: DataMapper<QuestionEntity, Question>,
    private val answerMapper: DataMapper<AnswerEntityBundle, Answer>
) : DataMapper<QuestionWithAnswersEntity, QuestionBundle> {
    override fun map(input: QuestionWithAnswersEntity): QuestionBundle {
        val answerA = input.answers.getOrNull(0)?.let {
            answerMapper.map(AnswerEntityBundle(it, AnswerType.A))
        }
        val answerB = input.answers.getOrNull(1)?.let {
            answerMapper.map(AnswerEntityBundle(it, AnswerType.B))
        }
        val answerC = input.answers.getOrNull(2)?.let {
            answerMapper.map(AnswerEntityBundle(it, AnswerType.C))
        }
        val answerD = input.answers.getOrNull(3)?.let {
            answerMapper.map(AnswerEntityBundle(it, AnswerType.D))
        }
        return QuestionBundle(
            time = 20,
            question = questionMapper.map(input.question),
            answers = listOfNotNull(answerA, answerB, answerC, answerD),
            difficulty = input.question.difficulty
        )
    }
}
