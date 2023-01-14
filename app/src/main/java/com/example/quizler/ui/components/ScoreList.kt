package com.example.quizler.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.quizler.ui.model.Score
import com.example.quizler.ui.screen.info.InfoScreen
import com.example.quizler.ui.screen.info.InfoScreenVariants
import com.example.quizler.ui.theme.QuizlerTheme
import com.example.quizler.ui.theme.spaceS

@Composable
fun ScoreList(
    modifier: Modifier = Modifier,
    spaceBetweenItems: Dp,
    list: List<Score>
) {
    if (list.isNotEmpty()) {
        LazyColumn(modifier = modifier, verticalArrangement = Arrangement.spacedBy(spaceBetweenItems)) {
            items(count = 1) {
                ScoreStands(
                    maxHeight = 300.dp,
                    first = list.getOrNull(0),
                    second = list.getOrNull(1),
                    third = list.getOrNull(2)
                )
            }
            items(items = list.drop(3), key = { it.id }) {
                ScoreCard(score = it)
                if (list.indexOf(it) == list.lastIndex) {
                    Spacer(modifier = Modifier.size(56.dp + spaceBetweenItems))
                }
            }
        }
    } else {
        InfoScreen(variant = InfoScreenVariants.NoScore)
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Preview(showSystemUi = true, showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewScoreList() {
    QuizlerTheme {
        Surface {
            ScoreList(
                spaceBetweenItems = spaceS,
                list = listOf(
                    Score(
                        id = "a", mode = "", username = "urkeev14", ranking = 1, score = 20
                    ),
                    Score(
                        id = "b", mode = "", username = "urkeev14", ranking = 2, score = 18
                    ),
                    Score(
                        id = "c", mode = "", username = "urkeev14", ranking = 3, score = 16
                    ),
                    Score(
                        id = "d", mode = "", username = "urkeev14", ranking = 4, score = 12
                    ),
                    Score(
                        id = "e", mode = "", username = "urkeev14", ranking = 5, score = 10
                    ),
                )
            )
        }
    }
}
