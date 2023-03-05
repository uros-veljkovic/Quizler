package com.example.quizler.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.quizler.R
import com.example.quizler.ui.model.Score
import com.example.quizler.ui.theme.QuizlerTheme
import com.example.quizler.ui.theme.manipulateColor
import com.example.quizler.ui.theme.spaceL
import com.example.quizler.ui.theme.spaceM
import com.example.quizler.ui.theme.spaceS

@Composable
fun ScoreStands(
    modifier: Modifier = Modifier,
    maxHeight: Dp,
    first: Score?,
    second: Score?,
    third: Score?
) {

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    Row(
        modifier = modifier
            .background(Color.Transparent)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom
    ) {
        second?.let {
            Stand(
                screenWidth / 3,
                maxHeight - 15.dp,
                MaterialTheme.shapes.medium.topStart,
                CornerSize(0.dp),
                R.drawable.ic_silver_medal,
                Color(0xffebe4db),
                it
            )
        }
        first?.let {
            Stand(
                screenWidth / 3,
                maxHeight,
                MaterialTheme.shapes.medium.topStart,
                MaterialTheme.shapes.medium.topStart,
                R.drawable.ic_gold_medal,
                Color(0xffffe27a),
                it
            )
        }
        third?.let {
            Stand(
                screenWidth / 3,
                maxHeight - 25.dp,
                CornerSize(0.dp),
                MaterialTheme.shapes.medium.topStart,
                R.drawable.ic_bronze_medal,
                Color(0xffc77c02),
                it
            )
        }
    }
}

@Composable
private fun Stand(
    width: Dp,
    height: Dp,
    leftCornerRadious: CornerSize,
    rightCorenrRadious: CornerSize,
    medal: Int,
    color: Color,
    score: Score
) {

    Column(modifier = Modifier.width(width)) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spaceS)
                .align(Alignment.CenterHorizontally),
            colors = CardDefaults.cardColors(containerColor = color, contentColor = Color.Black)
        ) {
            Text(
                text = score.username,
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spaceS),
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
            )
        }
        Spacer(modifier = Modifier.size(spaceM))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(height),
            shape = RoundedCornerShape(
                topStart = leftCornerRadious,
                topEnd = rightCorenrRadious,
                bottomStart = CornerSize(0.dp),
                bottomEnd = CornerSize(0.dp)
            ),
            colors = CardDefaults.cardColors(containerColor = color, contentColor = Color.Black)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    modifier = Modifier
                        .width(width - spaceM)
                        .padding(bottom = spaceL),
                    painter = painterResource(id = medal),
                    contentDescription = null
                )
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = color.manipulateColor(.9f),
                        contentColor = contentColorFor(
                            backgroundColor = color.manipulateColor(.9f)
                        )
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(spaceM),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier.wrapContentSize(),
                            text = score.score.toString(),
                            style = MaterialTheme.typography.headlineMedium,
                        )
                        Image(
                            modifier = Modifier.size(32.dp),
                            painter = painterResource(id = R.drawable.ic_coin),
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true, device = Devices.PIXEL_2)
@Preview(
    showSystemUi = true,
    showBackground = true,
    device = Devices.PIXEL_2,
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun PreviewScoreStands() {
    QuizlerTheme {
        Surface {

            ScoreStands(
                maxHeight = 300.dp,
                first = Score(
                    id = "",
                    username = "zhuabba_123",
                    score = 22,
                    ranking = 1,
                    mode = ""
                ),
                second = Score(
                    id = "",
                    username = "zhuabba_123",
                    score = 22,
                    ranking = 1,
                    mode = ""
                ),
                third = Score(
                    id = "",
                    username = "zhuabba_123",
                    score = 22,
                    ranking = 1,
                    mode = ""
                )
            )
        }
    }
}
