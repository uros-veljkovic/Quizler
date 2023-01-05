package com.example.quizler.ui.components.quiz

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.quizler.ui.theme.AlertRed
import com.example.quizler.ui.theme.QuizlerTheme
import com.example.quizler.ui.theme.SuccessGreen
import com.example.quizler.ui.theme.WarningOrange

@Composable
fun TimerComponent(
    modifier: Modifier = Modifier,
    progress: Float,
    time: String?,
    size: Dp,
    stroke: Dp,
) {

    val circularProgressColor = when {
        progress > .5f -> SuccessGreen()
        progress in (.25f)..(.5f) -> WarningOrange()
        else -> AlertRed()
    }
    val animatedProgressColor = animateColorAsState(
        targetValue = circularProgressColor,
        animationSpec = tween(200, 0, LinearEasing)
    )
    val animatedProgressValue by animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec,
    )

    Card(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
    ) {
        Box(
            modifier = Modifier
                .size(size)
                .clip(CircleShape),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = animatedProgressColor.value,
                progress = animatedProgressValue,
                modifier = Modifier.size(size),
                strokeWidth = stroke,
            )
            time?.let {
                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    text = time,
                    style = MaterialTheme.typography.titleLarge,
                )
            }
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Preview
@Composable
private fun PreviewCountDownIndicator() {
    QuizlerTheme {
        Surface {
            TimerComponent(progress = .7f, time = "20", size = 60.dp, stroke = 4.dp)
        }
    }
}
