package com.example.quizler.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quizler.R
import com.example.quizler.ui.model.ChosableItem
import com.example.quizler.ui.theme.QuizlerTheme
import com.example.quizler.ui.theme.spaceM
import com.example.quizler.ui.theme.spaceXS

@Composable
fun BasicDropdownMenu(
    modifier: Modifier = Modifier,
    isExpanded: Boolean,
    onExpandDropdown: (Boolean) -> Unit,
    chosenItem: ChosableItem.Content,
    items: List<ChosableItem>,
    onItemClick: (ChosableItem.Content) -> Unit,
) {
    Column {
        BasicDropdownChosenItem(
            onExpandDropdown = onExpandDropdown, isExpanded = isExpanded, chosenItem = chosenItem
        )
        Spacer(modifier = Modifier.size(spaceXS))
        Divider()
        Box(
            modifier = modifier.wrapContentSize(Alignment.BottomCenter),
            contentAlignment = Alignment.TopEnd
        ) {
            DropdownMenu(
                modifier = Modifier.fillMaxSize(),
                expanded = isExpanded,
                onDismissRequest = { onExpandDropdown(false) }
            ) {
                items.forEachIndexed { index, item ->
                    when (item) {
                        is ChosableItem.Title -> {
                            if (index != 0) {
                                Divider(modifier = Modifier.padding(spaceM))
                            }
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = stringResource(id = item.value),
                                style = MaterialTheme.typography.titleLarge,
                                textAlign = TextAlign.Center
                            )
                        }

                        is ChosableItem.Content -> {
                            DropdownMenuItem(text = {
                                Text(
                                    text = item.text,
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }, onClick = {
                                onItemClick(item)
                                onExpandDropdown(false)
                            }, leadingIcon = {
                                Image(
                                    modifier = Modifier.size(32.dp),
                                    painter = painterResource(id = item.icon),
                                    contentDescription = null
                                )
                            })
                            Spacer(modifier = Modifier.size(spaceM))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BasicDropdownChosenItem(
    modifier: Modifier = Modifier,
    onExpandDropdown: (Boolean) -> Unit,
    isExpanded: Boolean,
    chosenItem: ChosableItem.Content
) {
    Button(
        modifier = modifier.heightIn(70.dp),
        onClick = { onExpandDropdown(isExpanded.not()) }
    ) {
        Spacer(modifier = Modifier.size(spaceM))
        Image(
            modifier = Modifier.size(36.dp),
            painter = painterResource(id = chosenItem.icon),
            contentDescription = null
        )
        Spacer(modifier = Modifier.size(spaceM))
        Text(
            modifier = Modifier.weight(1f),
            text = chosenItem.text,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1
        )
        Spacer(modifier = Modifier.size(spaceM))
        Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewBasicMenuDropdown() {
    QuizlerTheme {
        Surface {
            BasicDropdownMenu(
                chosenItem = ChosableItem.Content(
                    itemId = "", icon = R.drawable.ic_mode_music, text = "Muzika"
                ),
                items = listOf(
                    ChosableItem.Title(value = R.string.tab_category),
                    ChosableItem.Content(
                        itemId = "", icon = R.drawable.ic_mode_sport, text = "Sport"
                    ),
                    ChosableItem.Content(
                        itemId = "", icon = R.drawable.ic_mode_music, text = "Muzika"
                    ),
                    ChosableItem.Content(
                        itemId = "", icon = R.drawable.ic_mode_movie, text = "Film"
                    ),
                    ChosableItem.Content(
                        itemId = "", icon = R.drawable.ic_mode_geography, text = "Geografija"
                    ),
                    ChosableItem.Content(
                        itemId = "", icon = R.drawable.ic_mode_history, text = "Istorija"
                    ),
                    ChosableItem.Title(value = R.string.tab_difficulty),
                    ChosableItem.Content(
                        itemId = "", icon = R.drawable.ic_mode_easy, text = "Lako"
                    ),
                    ChosableItem.Content(
                        itemId = "", icon = R.drawable.ic_mode_medium, text = "Srednje"
                    ),
                    ChosableItem.Content(
                        itemId = "", icon = R.drawable.ic_mode_hard, text = "Tesko"
                    ),
                    ChosableItem.Title(value = R.string.tab_length),
                    ChosableItem.Content(
                        itemId = "", icon = R.drawable.ic_mode_zen, text = "Kratko"
                    ),
                    ChosableItem.Content(
                        itemId = "", icon = R.drawable.ic_mode_exam, text = "Srednje"
                    ),
                    ChosableItem.Content(
                        itemId = "", icon = R.drawable.ic_mode_marathon, text = "Dugacko"
                    )
                ),
                onItemClick = {}, isExpanded = true, onExpandDropdown = {}
            )
        }
    }
}
