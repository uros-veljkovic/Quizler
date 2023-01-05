package com.example.quizler.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.quizler.ui.theme.QuizlerTheme

@Composable
fun Coin(size: Dp) {

    Box(
        modifier = Modifier
            .clip(CircleShape)
            .size(size),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.fillMaxSize(),
            progress = 1f,
            color = Color.Blue,
        )
        Logo(modifier = Modifier.fillMaxSize())
    }
}

@Preview
@Composable
fun PreviewCoin() {
    QuizlerTheme {
        Coin(100.dp)
    }
}
