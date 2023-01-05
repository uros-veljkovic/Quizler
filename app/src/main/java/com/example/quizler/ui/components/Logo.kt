package com.example.quizler.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.quizler.R
import com.example.quizler.ui.theme.QuizlerTheme

@Composable
fun Logo(modifier: Modifier = Modifier, size: Dp = 240.dp) {
    Column(
        modifier = modifier.size(size),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = null,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewLogo() {
    QuizlerTheme {
        Logo()
    }
}
