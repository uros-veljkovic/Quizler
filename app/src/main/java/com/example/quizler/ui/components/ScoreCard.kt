package com.example.quizler.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quizler.R
import com.example.quizler.ui.model.Score
import com.example.quizler.ui.theme.QuizlerTheme
import com.example.quizler.ui.theme.spaceM
import com.example.quizler.ui.theme.spaceS

@Composable
fun ScoreCard(
    score: Score
) {

    Card {
        Row(
            modifier = Modifier.padding(spaceS),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RankingBadgeItem(ranking = score.ranking, rankingColor = score.getRankingColor())
            Spacer(modifier = Modifier.size(spaceM))
            Text(
                modifier = Modifier.weight(1f),
                text = score.username,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.size(spaceM))
            Text(
                modifier = Modifier.wrapContentHeight(),
                text = score.score.toString(),
                style = MaterialTheme.typography.titleLarge,
            )
            Spacer(modifier = Modifier.size(spaceM))
            Image(
                modifier = Modifier.size(32.dp),
                painter = painterResource(id = R.drawable.ic_coin),
                contentDescription = null
            )
        }
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewScoreCard() {
    QuizlerTheme {
        Surface {
            ScoreCard(
                Score(
                    id = "",
                    mode = "",
                    username = "urkeev14",
                    ranking = 6,
                    score = 20
                )
            )
        }
    }
}
