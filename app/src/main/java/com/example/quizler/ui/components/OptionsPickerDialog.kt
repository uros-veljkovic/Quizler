package com.example.quizler.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quizler.R
import com.example.quizler.ui.model.IChoosableOptionItem
import com.example.quizler.ui.model.InfoBannerData
import com.example.quizler.ui.theme.QuizlerTheme
import com.example.quizler.ui.theme.colorAcomodatedToLightOrDarkMode
import com.example.quizler.ui.theme.spaceL
import com.example.quizler.ui.theme.spaceM
import com.example.quizler.ui.theme.spaceXL

@Composable
fun OptionsPickerDialog(
    modifier: Modifier = Modifier,
    data: InfoBannerData,
    items: List<IChoosableOptionItem>,
    onChooseItem: (IChoosableOptionItem) -> Unit,
    onConfirm: () -> Unit
) {

    val backgroundColor = colorAcomodatedToLightOrDarkMode(color = data.color)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Gray.copy(alpha = .5f),
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(start = spaceXL, end = spaceXL),
            colors = CardDefaults.cardColors(containerColor = backgroundColor)
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
                        modifier = Modifier.size(60.dp),
                        painter = painterResource(id = data.icon),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.size(spaceL))
                    Column {
                        Text(
                            text = stringResource(id = data.title),
                            style = MaterialTheme.typography.titleLarge,
                        )
                        data.description?.let {
                            Text(
                                text = stringResource(id = it),
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.size(spaceM))
                LazyColumn {
                    items(items = items, key = { it.getItemId() }) {
                        OptionItem(item = it, onChosen = onChooseItem)
                        Divider(thickness = 1.dp, color = backgroundColor.manipulateColor(.9f))
                    }
                }
                Spacer(modifier = Modifier.size(spaceM))
                Button(modifier = Modifier.fillMaxWidth(), onClick = onConfirm) {
                    Text(text = stringResource(id = R.string.confirm))
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Preview(showBackground = true, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewOptionsPickerDialog() {
    QuizlerTheme {
        Surface {
            OptionsPickerDialog(
                modifier = Modifier,
                data = InfoBannerData.ReportQuestion,
                items = mutableListOf<IChoosableOptionItem>().apply {
                    repeat(4) {
                        add(object : IChoosableOptionItem {
                            override fun getItemId(): String {
                                return "id:$it"
                            }

                            override fun getTitle(): String {
                                return "This is some random description"
                            }

                            override fun getIsChosen(): Boolean {
                                return it % 2 == 0
                            }
                        })
                    }
                },
                onChooseItem = {},
                onConfirm = {}
            )
        }
    }
}
