package com.example.quizler.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quizler.R
import com.example.quizler.domain.model.QuizMode
import com.example.quizler.ui.theme.QuizlerTheme
import com.example.quizler.ui.theme.spaceS
import com.example.quizler.ui.theme.spaceXS

@Composable
fun QuizModeCard(
    modifier: Modifier = Modifier,
    mode: QuizMode,
    onModeSelected: (QuizMode) -> Unit
) {
    Button(
        modifier = modifier.wrapContentSize(),
        shape = RoundedCornerShape(16.dp),
        onClick = { onModeSelected(mode) }
    ) {
        Column(
            modifier = Modifier.padding(spaceS), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(60.dp),
                painter = painterResource(id = mode.icon),
                contentDescription = "Image"
            )
            Spacer(modifier = Modifier.size(spaceXS))
            Text(
                text = stringResource(id = mode.title),
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Center,
                softWrap = true
            )
        }
    }
}

@Preview
@Composable
fun PreviewQuizModeCard() {
    QuizlerTheme {
        QuizModeCard(
            mode = QuizMode(
                "id",
                R.string.mode_title_general_knowledge,
                R.drawable.ic_mode_exam,
                "20",
            ),
            onModeSelected = {}
        )
    }
}
