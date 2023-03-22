package com.example.quizler.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.quizler.extensions.toSp
import com.example.quizler.theme.QuizlerTheme

@Composable
fun RankingBadgeItem(
    modifier: Modifier = Modifier,
    ranking: Int,
    rankingColor: Color,
    size: Dp = 50.dp,
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(rankingColor)
            .padding(top = 1.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = ranking.toString(),
            style = MaterialTheme.typography.titleLarge.copy(fontSize = size.toSp() / 2),
            color = contentColorFor(backgroundColor = rankingColor)
        )
    }
}

@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewAvatarItem() {
    QuizlerTheme {
        Surface {
            RankingBadgeItem(ranking = 6, rankingColor = MaterialTheme.colorScheme.surfaceVariant)
        }
    }
}
