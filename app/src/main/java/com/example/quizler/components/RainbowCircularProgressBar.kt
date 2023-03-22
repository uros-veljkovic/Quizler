package com.example.quizler.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quizler.theme.QuizlerTheme
import com.example.quizler.theme.RandomColors

@Composable
fun RainbowCircularProgressBar() {
    Box(modifier = Modifier.wrapContentSize(), contentAlignment = Alignment.Center) {
        RandomColors.reversed().forEachIndexed { index, color ->
            val size = ((index * 4) + 8).dp
            CircularProgressIndicator(modifier = Modifier.size(size), color = color)
        }
    }
}

@Preview
@Composable
fun PreviewRainbow() {
    QuizlerTheme {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            RainbowCircularProgressBar()
        }
    }
}
