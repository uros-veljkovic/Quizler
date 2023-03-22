package com.example.quizler.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.domain.model.AnswerType
import com.example.quizler.theme.QuizlerTheme
import com.example.quizler.theme.colorAcomodatedToLightOrDarkMode
import com.example.quizler.theme.spaceS
import com.example.quizler.theme.successGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CorrectAnswerPicker(
    modifier: Modifier = Modifier,
    correctAnswerType: AnswerType,
    onCorrectAnswerChosen: (AnswerType) -> Unit = {}
) {
    val answers = rememberSaveable { AnswerType.values() }
    Column(modifier = modifier) {
        Text(text = stringResource(id = com.example.quizler.R.string.choose_correct_answer))
        Spacer(modifier = Modifier.size(spaceS))
        Row(
            horizontalArrangement = Arrangement.spacedBy(spaceS),
            verticalAlignment = Alignment.CenterVertically
        ) {
            answers.forEach { answer ->
                FilterChip(
                    modifier = Modifier.weight(1f), onClick = { onCorrectAnswerChosen(answer) },
                    label = {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 8.dp),
                            text = answer.char.toString(),
                            textAlign = TextAlign.Center
                        )
                    },
                    selected = correctAnswerType == answer, leadingIcon = null,
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = colorAcomodatedToLightOrDarkMode(color = successGreen())
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        selectedBorderColor = MaterialTheme.colorScheme.primary,
                        selectedBorderWidth = 2.dp
                    )
                )
            }
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Preview
@Composable
fun PreviewCorrectAnswerPicker() {
    QuizlerTheme {
        Surface {
            CorrectAnswerPicker(correctAnswerType = AnswerType.B)
        }
    }
}
