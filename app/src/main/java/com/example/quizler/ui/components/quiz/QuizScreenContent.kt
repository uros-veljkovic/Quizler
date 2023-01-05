package com.example.quizler.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.layoutId
import com.example.quizler.R
import com.example.quizler.domain.model.Answer
import com.example.quizler.domain.model.AnswerType
import com.example.quizler.domain.model.Difficulty
import com.example.quizler.domain.model.Question
import com.example.quizler.ui.components.quiz.AnswerButton
import com.example.quizler.ui.components.quiz.QuestionTextComponent
import com.example.quizler.ui.screen.home.plus
import com.example.quizler.ui.screen.quiz.QuestionBundle
import com.example.quizler.ui.screen.quiz.QuizScreenState
import com.example.quizler.ui.screen.quiz.map
import com.example.quizler.ui.theme.QuizlerTheme
import com.example.quizler.ui.theme.spaceS


@Composable
fun QuestionScreenContent(
    padding: PaddingValues,
    state: QuizScreenState,
    onQuestionAnswered: (AnswerType) -> Unit,
    onReportQuestion: (String) -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding + spaceS),
        constraintSet = quizScreenConstraintSet
    ) {
        QuestionTextComponent(
            modifier = Modifier.layoutId("question"),
            text = state.question?.question?.text ?: "",
            timeLeft = state.question?.time ?: 0,
            span = state.questionNumberSpan,
            points = state.points
        )
        state.question?.answers?.forEachIndexed { index, answer ->
            AnswerButton(
                modifier = Modifier.layoutId(map[index] ?: ""),
                answer = answer,
                isAnyAnswerChoose = state.isAnyAnswerChosen,
                onAnswered = onQuestionAnswered
            )
        }
        AnimatedVisibility(
            modifier = Modifier.layoutId("reportButton"),
            visible = state.isReportQuestionButtonVisible
        ) {
            ExtendedFloatingActionButton(
                onClick = { state.question?.question?.id?.let { id -> onReportQuestion(id) } },
                icon = {
                    Icon(imageVector = Icons.Default.Warning, contentDescription = null)
                },
                text = {
                    Text(text = stringResource(id = R.string.report_question))
                }
            )
        }
    }
}

val quizScreenConstraintSet = ConstraintSet {
    val question = createRefFor("question")
    val answerA = createRefFor("answerA")
    val answerB = createRefFor("answerB")
    val answerC = createRefFor("answerC")
    val answerD = createRefFor("answerD")
    val reportButton = createRefFor("reportButton")


    constrain(question) {
        top.linkTo(parent.top)
        centerHorizontallyTo(parent)
    }
    constrain(answerA) {
        centerHorizontallyTo(parent)
    }
    constrain(answerB) {
        centerHorizontallyTo(parent)
    }
    constrain(answerC) {
        centerHorizontallyTo(parent)
    }
    constrain(answerD) {
        centerHorizontallyTo(parent)
    }
    constrain(reportButton) {
        centerHorizontallyTo(parent)
    }
    createVerticalChain(question, answerA, answerB, answerC, answerD, reportButton)
}

@Preview(showBackground = true)
@Preview(showBackground = true, device = Devices.PIXEL_3)
@Preview(showBackground = true, device = Devices.PIXEL_4_XL)
@Composable
fun PreviewQuestionScreenContent() {
    QuizlerTheme() {
        QuestionScreenContent(
            padding = PaddingValues(),
            state = QuizScreenState(
                question = QuestionBundle(
                    time = 20,
                    question = Question("", "This is question", ""),
                    answers = mutableListOf<Answer>().apply {
                        repeat(4) {
                            add(Answer("1", "This is some text", type = AnswerType.A))
                        }
                    },
                    difficulty = Difficulty.Easy
                ),
                isReportQuestionButtonVisible = true
            ),
            onQuestionAnswered = {},
            onReportQuestion = {},
        )
    }
}