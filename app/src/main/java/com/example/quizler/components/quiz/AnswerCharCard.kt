package com.example.quizler.components.quiz

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quizler.theme.QuizlerTheme

@Composable
fun AnswerCharComponent(
    modifier: Modifier = Modifier,
    char: Char,
) {
    Row(
        modifier = modifier.background(MaterialTheme.colorScheme.surfaceVariant, MaterialTheme.shapes.small),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = char.toString(),
            color = contentColorFor(backgroundColor = MaterialTheme.colorScheme.surfaceVariant),
            style = MaterialTheme.typography.titleMedium,
        )
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Preview
@Composable
fun PreviewAnswerCharComponent() {
    QuizlerTheme {
        AnswerCharComponent(
            modifier = Modifier
                .height(50.dp)
                .width(50.dp),
            char = 'A'
        )
    }
}
