package com.example.quizler.ui.components.quiz

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.quizler.ui.theme.QuizlerTheme

@Composable
fun AnswerCharComponent(
    size: Dp,
    char: Char,
) {
    Card(
        shape = MaterialTheme.shapes.small
    ) {
        Box(
            modifier = Modifier.size(size), contentAlignment = Alignment.Center
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
        AnswerCharComponent(size = 40.dp, char = 'A')
    }
}
