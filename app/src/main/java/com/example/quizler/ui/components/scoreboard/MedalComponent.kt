package com.example.quizler.ui.components.scoreboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import com.example.quizler.R

@Composable
fun MedalComponent(
    modifier: Modifier = Modifier,
    size: Dp,
    positionOnScoreboard: Int,
) {
    val medal = when (positionOnScoreboard) {
        1 -> {
            R.drawable.ic_gold_medal
        }
        2 -> {
            R.drawable.ic_silver_medal
        }
        3 -> {
            R.drawable.ic_bronze_medal
        }
        else -> {
            null
        }
    }
    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        if (medal != null) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                painter = painterResource(id = medal),
                contentDescription = null,
            )
        } else {
            Text(
                text = positionOnScoreboard.toString().plus("."),
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}
