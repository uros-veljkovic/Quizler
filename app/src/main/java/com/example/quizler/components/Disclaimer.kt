package com.example.quizler.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.quizler.R
import com.example.quizler.theme.QuizlerTheme
import com.example.quizler.theme.spaceM
import com.example.quizler.theme.spaceS

@Composable
fun Disclaimer(
    modifier: Modifier = Modifier,
    dividerWidth: Dp = 3.dp,
    text: String,
    color: Color = MaterialTheme.colorScheme.onSurface
) {
    val currentLocalDensity = LocalDensity.current
    var textSize by remember { mutableStateOf(Dp.Unspecified) }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Divider(
            modifier = Modifier
                .width(dividerWidth)
                .height(textSize + spaceS),
            color = color
        )
        Text(
            modifier = Modifier
                .padding(start = spaceS)
                .onGloballyPositioned { ts ->
                    textSize = with(currentLocalDensity) {
                        ts.size.height.toDp()
                    }
                },
            text = text
        )
    }
}

@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewDisclaimer() {
    QuizlerTheme {
        Surface {
            Disclaimer(
                modifier = Modifier.padding(spaceM),
                dividerWidth = 4.dp,
                text = stringResource(id = R.string.lorem)
            )
        }
    }
}
