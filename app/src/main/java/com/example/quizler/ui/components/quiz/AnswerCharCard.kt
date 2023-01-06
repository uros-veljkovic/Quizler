package com.example.quizler.ui.components.quiz

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.quizler.ui.theme.QuizlerTheme

@Composable
fun AnswerCharComponent(
    modifier: Modifier = Modifier,
    char: Char,
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.small
    ) {
        Box(
            modifier = Modifier
                .aspectRatio(1f),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = char.toString(),
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}

@Preview
@Composable
fun PreviewAnswerCharComponent() {
    QuizlerTheme {
        AnswerCharComponent(char = 'A')
    }
}
