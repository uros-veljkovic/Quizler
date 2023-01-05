package com.example.quizler.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quizler.R
import com.example.quizler.ui.theme.QuizlerTheme

@Composable
fun BasicDialog(
    @DrawableRes icon: Int,
    title: String,
    description: String? = null,
    content: @Composable () -> Unit = {},
    positiveButtonText: String? = null,
    negativeButtonText: String? = null,
    onPositiveButtonClick: () -> Unit = {},
    onNegativeButtonClick: () -> Unit = {},
) {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.Gray.copy(alpha = .5f)) {
        Card(
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .padding(10.dp, 5.dp, 10.dp, 10.dp)
                .wrapContentSize(),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column {
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .padding(top = 35.dp)
                        .height(70.dp)
                        .fillMaxWidth(),
                )
                BasicDialogText(title, description)
                content.invoke()
                BasicDialogButtons(
                    negativeButtonText,
                    onNegativeButtonClick,
                    positiveButtonText,
                    onPositiveButtonClick
                )
            }
        }
    }
}

@Composable
private fun BasicDialogText(title: String, description: String?) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = title,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 5.dp)
                .fillMaxWidth(),
            style = MaterialTheme.typography.titleLarge,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        description?.let {
            Text(
                text = it,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                    .fillMaxWidth(),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun BasicDialogButtons(
    negativeButtonText: String?,
    onNegativeButtonClick: () -> Unit = {},
    positiveButtonText: String?,
    onPositiveButtonClick: () -> Unit = {}
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        negativeButtonText?.let {
            TextButton(
                modifier = Modifier.weight(1f),
                onClick = onNegativeButtonClick::invoke
            ) {
                Text(
                    text = it,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                )
            }
        }
        positiveButtonText?.let {
            TextButton(
                modifier = Modifier.weight(1f),
                onClick = onPositiveButtonClick::invoke
            ) {
                Text(
                    text = it,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Preview(showBackground = true, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewQuizFinishedDialog() {
    QuizlerTheme {
        Surface {
            BasicDialog(
                icon = R.drawable.ic_in_love,
                title = stringResource(R.string.wow),
                description = stringResource(R.string.not_bad),
                positiveButtonText = stringResource(R.string.yes),
                negativeButtonText = stringResource(R.string.no)
            )
        }
    }
}
