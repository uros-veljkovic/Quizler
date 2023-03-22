package com.example.quizler.ui.screen.quiz

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.layoutId
import com.example.domain.model.Answer
import com.example.domain.model.AnswerType
import com.example.domain.model.Difficulty
import com.example.domain.model.Question
import com.example.domain.model.QuestionWithAnswers
import com.example.quizler.R
import com.example.quizler.components.quiz.AnswerButton
import com.example.quizler.components.quiz.QuestionTextComponent
import com.example.quizler.theme.QuizlerTheme
import com.example.quizler.theme.spaceS

@Composable
fun QuestionScreenContent(
    state: QuizScreenState,
    onQuestionAnswered: (AnswerType) -> Unit,
    onReportQuestion: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(spaceS),
        constraintSet = quizScreenConstraintSet
    ) {
        QuestionTextComponent(
            modifier = Modifier.layoutId("question"),
            text = state.question?.question?.text ?: "",
            timeLeft = state.question?.time ?: 0,
            span = state.questionNumberSpan,
            points = state.points
        )
        LazyColumn(
            modifier = Modifier
                .layoutId("answers"),
            verticalArrangement = Arrangement.spacedBy(spaceS)
        ) {
            items(state.question?.answers ?: emptyList(), key = { it.id }) {
                AnswerButton(
                    modifier = Modifier,
                    answer = it,
                    isAnyAnswerChoose = state.isAnyAnswerChosen,
                    onAnswered = onQuestionAnswered
                )
            }
        }
        AnimatedVisibility(
            modifier = Modifier.layoutId("reportButton"),
            visible = state.isInvalidQuestionReportButtonVisible()
        ) {
            ExtendedFloatingActionButton(
                onClick = onReportQuestion,
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
    val answers = createRefFor("answers")
    val reportButton = createRefFor("reportButton")

    constrain(question) {
        top.linkTo(parent.top)
        centerHorizontallyTo(parent)
    }
    constrain(answers) {
        centerHorizontallyTo(parent)
    }
    constrain(reportButton) {
        centerHorizontallyTo(parent)
    }
    createVerticalChain(question, answers, reportButton)
}

@Preview(showBackground = true)
@Preview(showBackground = true, device = Devices.PIXEL_3, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewQuestionScreenContent() {
    QuizlerTheme {
        Surface {
            QuestionScreenContent(
                state = QuizScreenState(
                    question = QuestionWithAnswers(
                        time = 20,
                        question = Question("", "This is question", ""),
                        answers = mutableListOf<Answer>().apply {
                            repeat(4) {
                                add(Answer(Math.random().toString(), "This is some text", type = AnswerType.A))
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
}
