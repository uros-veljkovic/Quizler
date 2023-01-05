package com.example.quizler.ui.components.quiz

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FilterChip
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.example.quizler.R
import com.example.quizler.ui.theme.QuizlerTheme
import com.example.quizler.ui.theme.spaceM
import com.example.quizler.ui.theme.spaceS

data class QuestionNumberSpan(
    val currentQuestion: Int = 0,
    val totalQuestions: Int = 0,
)

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun QuestionTextComponent(
    modifier: Modifier = Modifier,
    text: String,
    timeLeft: Int?,
    timerSize: Dp = 60.dp,
    span: QuestionNumberSpan,
    points: Int
) {

    ConstraintLayout(
        modifier = modifier,
        constraintSet = questionTextComponentConstrains()
    ) {
        Card(
            modifier = Modifier
                .layoutId(idCard)
                .height(200.dp)
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
        ) {}
        Text(
            modifier = Modifier
                .layoutId(idQuestionText)
                .padding(spaceM),
            text = text,
            maxLines = 4,
            style = MaterialTheme.typography.titleMedium,
        )
        FilterChip(
            modifier = Modifier
                .layoutId(idChipQuestinNumberSpan)
                .padding(start = spaceS),
            selected = true,
            onClick = { }
        ) {
            Text(
                modifier = Modifier.padding(top = 2.dp),
                text = "Pitanje " + span.currentQuestion.toString() + " / " + span.totalQuestions,
                style = MaterialTheme.typography.labelLarge
            )
        }
        FilterChip(
            modifier = Modifier
                .layoutId(idChipPoints)
                .wrapContentSize()
                .padding(end = spaceS),
            content = {
                Text(
                    modifier = Modifier.padding(top = 2.dp),
                    text = points.toString(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelLarge,
                )
            }, trailingIcon = {
                Image(
                    painter = painterResource(id = R.drawable.ic_coin),
                    contentDescription = null
                )
            }, selected = true, onClick = {}
        )

        TimerComponent(
            Modifier.layoutId(idTimer),
            progress = timeLeft!!.toFloat() / 20f,
            time = timeLeft.toString(),
            size = timerSize,
            stroke = 4.dp
        )
    }
}

private const val idCard = "card"
private const val idQuestionText = "questionText"
private const val idChipQuestinNumberSpan = "questionNumberSpan"
private const val idChipPoints = "points"
private const val idTimer = "timer"

fun questionTextComponentConstrains() = ConstraintSet {
    val card = createRefFor(idCard)
    val questionText = createRefFor(idQuestionText)
    val questionNumberSpan = createRefFor(idChipQuestinNumberSpan)
    val points = createRefFor(idChipPoints)
    val timer = createRefFor(idTimer)

    constrain(card) {
        centerHorizontallyTo(parent)
        top.linkTo(parent.top)
    }
    constrain(questionText) {
        centerHorizontallyTo(card)
        centerVerticallyTo(card)
        top.linkTo(parent.top)
    }
    constrain(questionNumberSpan) {
        start.linkTo(card.start)
        centerVerticallyTo(points)
    }
    constrain(points) {
        end.linkTo(card.end)
        top.linkTo(card.top)
    }
    constrain(timer) {
        centerHorizontallyTo(card)
        top.linkTo(card.bottom)
        bottom.linkTo(card.bottom)
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Preview(showSystemUi = true, showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewQuestionTextComponent() {
    QuizlerTheme {
        Surface {
            QuestionTextComponent(
                text = stringResource(id = R.string.lorem),
                timerSize = 60.dp,
                timeLeft = 20,
                span = QuestionNumberSpan(1, 20),
                points = 17774
            )
        }
    }
}
