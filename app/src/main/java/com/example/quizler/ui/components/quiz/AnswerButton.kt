package com.example.quizler.ui.components.quiz

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quizler.domain.model.Answer
import com.example.quizler.domain.model.AnswerType
import com.example.quizler.ui.theme.AlertRed
import com.example.quizler.ui.theme.QuizlerTheme
import com.example.quizler.ui.theme.SuccessGreen
import com.example.quizler.ui.theme.colorAcomodatedToLightOrDarkMode
import com.example.quizler.ui.theme.spaceM

@Composable
fun AnswerButton(
    modifier: Modifier = Modifier,
    answer: Answer,
    isAnyAnswerChoose: Boolean?,
    onAnswered: (AnswerType) -> Unit,
) {
    val surfaceColor = MaterialTheme.colorScheme.primary
    val color = when {
        answer.isCorrect && isAnyAnswerChoose != null -> SuccessGreen()
        answer.isCorrect.not() && answer.isChosen -> AlertRed()
        else -> surfaceColor
    }

    val animationTime = if (answer.isChosen) 500 else 100
    val animatedButtonColor = animateColorAsState(
        targetValue = colorAcomodatedToLightOrDarkMode(color = color),
        animationSpec = tween(animationTime, 0, LinearEasing)
    )

    Button(
        modifier = modifier
            .heightIn(60.dp, 100.dp)
            .fillMaxHeight(),
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(
            contentColor = contentColorFor(animatedButtonColor.value),
            containerColor = animatedButtonColor.value,
            disabledContentColor = contentColorFor(animatedButtonColor.value),
            disabledContainerColor = animatedButtonColor.value,
        ),
        enabled = isAnyAnswerChoose == null,
        onClick = {
            onAnswered(answer.type)
        },
        contentPadding = PaddingValues(spaceM)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AnswerCharComponent(
                modifier = Modifier
                    .height(60.dp)
                    .width(60.dp),
                char = answer.type.char
            )
            Spacer(modifier = Modifier.size(spaceM))
            Text(
                modifier = Modifier
                    .padding(start = 4.dp)
                    .wrapContentHeight()
                    .weight(1f)
                    .align(Alignment.CenterVertically),
                color = contentColorFor(backgroundColor = animatedButtonColor.value),
                text = answer.text,
                maxLines = 3
            )
        }
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewAnswerTextComponent() {
    QuizlerTheme {
        Surface {
            AnswerButton(
                answer = Answer(
                    id = "",
                    text = "This is some answer",
                    type = AnswerType.A,
                    isCorrect = false,
                    isChosen = true
                ),
                isAnyAnswerChoose = true, onAnswered = {}
            )
        }
    }
}
