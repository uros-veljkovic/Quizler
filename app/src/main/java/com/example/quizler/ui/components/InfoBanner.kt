package com.example.quizler.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.example.quizler.R
import com.example.quizler.ui.theme.QuizlerTheme
import com.example.quizler.ui.theme.spaceL
import com.example.quizler.ui.theme.spaceM

@Composable
fun InfoBanner(
    modifier: Modifier = Modifier,
    data: InfoBannerData,
    isActionButtonEnabled: Boolean? = true,
    isActionButtonVisible: Boolean? = true,
    onActionButtonClick: (() -> Unit)? = null,
) {

    val buttonColor = data.color.manipulateColor(.4f)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
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
                    colors = ButtonDefaults.buttonColors(
                        containerColor = buttonColor
                    )
                ) {
                    Text(
                        text = stringResource(id = data.actionButtonText),
                    )
                }
            }
        }
    }
}

fun Color.manipulateColor(factor: Float): Color {
    return Color(red = red * factor, blue = blue * factor, green = green * factor)
}

@Preview(device = Devices.PIXEL_3, showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Preview(device = Devices.PIXEL_3, showBackground = true)
@Composable
fun PreviewInfoBanner() {
    QuizlerTheme {
        Surface {
            Column(Modifier.padding(spaceM)) {
                InfoBanner(data = InfoBannerData.NoNetwork)
                Spacer(modifier = Modifier.size(spaceM))
                InfoBanner(data = InfoBannerData.ErrorLoadingContent)
                Spacer(modifier = Modifier.size(spaceM))
                InfoBanner(data = InfoBannerData.Loading)
            }
        }
    }
}

sealed class InfoBannerData(
    @DrawableRes val icon: Int,
    @StringRes val title: Int,
    @StringRes val description: Int? = null,
    val color: Color,
    @StringRes val actionButtonText: Int? = null,
) {
    object NoNetwork : InfoBannerData(
        icon = R.drawable.ic_no_wifi,
        title = R.string.no_internet_connection,
        description = R.string.no_internet_connection_description,
        color = Color(0xffef9a9a),
        actionButtonText = R.string.try_again
    )

    object ErrorLoadingContent : InfoBannerData(
        icon = R.drawable.ic_no_content,
        title = R.string.error_unknow,
        description = R.string.error_unknow_description,
        color = Color(0xffffcc80),
    )

    object Loading : InfoBannerData(
        icon = R.drawable.ic_sand_clock, title = R.string.loading, color = Color(0xff90caf9)
    )
}
