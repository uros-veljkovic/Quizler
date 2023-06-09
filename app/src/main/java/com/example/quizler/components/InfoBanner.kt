package com.example.quizler.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.example.quizler.model.InfoBannerData
import com.example.quizler.theme.QuizlerTheme
import com.example.quizler.theme.spaceL
import com.example.quizler.theme.spaceM

@Composable
fun InfoBanner(
    modifier: Modifier = Modifier,
    data: InfoBannerData?,
    isActionButtonEnabled: Boolean? = true,
    isActionButtonVisible: Boolean? = true,
    onActionButtonClick: (() -> Unit)? = null,
) {
    AnimatedVisibility(visible = data != null) {
        data?.let {
            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(start = spaceM, end = spaceM, bottom = spaceM),
                colors = CardDefaults.cardColors(
                    containerColor = data.color,
                    contentColor = contentColorFor(backgroundColor = MaterialTheme.colorScheme.onSurface)
                )
            ) {
                Column(
                    modifier = Modifier.padding(spaceM),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier,
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = data.icon),
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.size(spaceL))
                        Column {
                            Text(
                                text = stringResource(id = data.title),
                                style = MaterialTheme.typography.titleLarge,
                                color = contentColorFor(backgroundColor = data.color)
                            )
                            data.description?.let {
                                Text(
                                    text = stringResource(id = it),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = contentColorFor(backgroundColor = data.color)
                                )
                            }
                        }
                    }
                    if (isActionButtonVisible == true && data.actionButtonText != null) {
                        Spacer(modifier = Modifier.size(spaceM))
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            enabled = isActionButtonEnabled ?: true,
                            onClick = { onActionButtonClick?.invoke() },
                        ) {
                            Text(
                                text = stringResource(id = data.actionButtonText),
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(device = Devices.PIXEL_3, showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Preview(device = Devices.PIXEL_3, showBackground = true)
@Composable
fun PreviewInfoBanner() {
    QuizlerTheme {
        Surface {
            Column(Modifier.padding(spaceM)) {
                InfoBanner(data = InfoBannerData.NoNetwork, isActionButtonEnabled = false)
                Spacer(modifier = Modifier.size(spaceM))
                InfoBanner(data = InfoBannerData.ErrorLoadingContent)
                Spacer(modifier = Modifier.size(spaceM))
                InfoBanner(data = InfoBannerData.Loading)
            }
        }
    }
}
