package com.example.quizler.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quizler.R
import com.example.quizler.theme.QuizlerTheme
import com.example.quizler.theme.spaceM
import com.example.quizler.ui.screen.settings.SettingsItem
import com.example.quizler.ui.screen.settings.SettingsItemEvent

@Composable
fun SettingsButton(
    button: SettingsItem.Button,
    onItemClick: (SettingsItemEvent) -> Unit
) {
    Card(
        modifier = Modifier
            .clickable {
                onItemClick(button.event)
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.weight(.15f),
                painter = painterResource(id = button.frontIconRes),
                contentDescription = null
            )
            Text(modifier = Modifier.weight(.85f), text = stringResource(id = button.titleStringRes))
        }
    }
}

@Composable
fun SettingsButtonGroup(
    group: SettingsItem.ButtonGroup,
    onItemClick: (SettingsItemEvent) -> Unit
) {
    Card {
        Column(modifier = Modifier) {
            group.buttons.forEachIndexed { index, item ->
                Row(
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth()
                        .clickable {
                            onItemClick(item.event)
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.weight(.15f),
                        painter = painterResource(id = item.frontIconRes),
                        contentDescription = null
                    )
                    Text(modifier = Modifier.weight(.85f), text = stringResource(id = item.titleStringRes))
                }
                if (group.buttons.lastIndex != index) {
                    Divider(
                        Modifier.padding(start = spaceM, end = spaceM),
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = .3f)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewSettingsItem() {
    QuizlerTheme {
        Surface {
            SettingsButton(
                button = SettingsItem.Button(
                    R.string.settings_item_rate_app,
                    R.drawable.ic_star,
                    SettingsItemEvent.RateApp
                ),
                onItemClick = {}
            )
        }
    }
}

@Preview
@Composable
fun PreviewSettingsItemGroup() {
    QuizlerTheme {
        Surface {
            SettingsButtonGroup(
                group = SettingsItem.ButtonGroup(
                    listOf(
                        SettingsItem.Button(
                            R.string.settings_item_rate_app,
                            R.drawable.ic_star,
                            SettingsItemEvent.RateApp
                        ),
                        SettingsItem.Button(
                            R.string.settings_item_rate_app,
                            R.drawable.ic_star,
                            SettingsItemEvent.RateApp
                        ),
                        SettingsItem.Button(
                            R.string.settings_item_rate_app,
                            R.drawable.ic_star,
                            SettingsItemEvent.RateApp
                        ),
                    )
                ),
                onItemClick = {}
            )
        }
    }
}
