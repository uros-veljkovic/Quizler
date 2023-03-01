package com.example.quizler.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
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
import com.example.quizler.ui.model.ChosableItem
import com.example.quizler.ui.theme.QuizlerTheme
import com.example.quizler.ui.theme.spaceM
import com.example.quizler.ui.theme.spaceS

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HorizontalChipPicker(
    modifier: Modifier = Modifier,
    isDropdownExpanded: Boolean = false,
    items: List<ChosableItem.Content> = emptyList(),
    chosenItem: ChosableItem.Content? = null,
    onItemClick: (ChosableItem.Content) -> Unit,
    onExpandDropdown: () -> Unit = {},
) {
    val scrollState = rememberLazyListState()
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.clickable {
                onExpandDropdown()
            },
            horizontalArrangement = Arrangement.spacedBy(spaceM),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Disclaimer(
                modifier = Modifier.wrapContentSize(),
                dividerWidth = 3.dp,
                text = "Izabrana kategorija: ",
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.weight(1f))
            chosenItem?.icon?.let {
                Image(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(id = it),
                    contentDescription = null
                )
            }
            chosenItem?.text?.let {
                Text(text = it)
            }
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null
            )
        }
        AnimatedVisibility(visible = isDropdownExpanded) {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(spaceS), state = scrollState) {
                items(items = items, key = { it.itemId }) {
                    FilterChip(
                        onClick = {
                            onItemClick(it)
                        },
                        selected = chosenItem?.itemId == it.itemId,
                        label = {
                            Text(text = it.text)
                        },
                        leadingIcon = {
                            Image(
                                modifier = Modifier.size(20.dp),
                                painter = painterResource(id = it.icon),
                                contentDescription = null
                            )
                        },
                        border = FilterChipDefaults.filterChipBorder(
                            selectedBorderColor = MaterialTheme.colorScheme.primary,
                            selectedBorderWidth = 2.dp
                        )
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewHorizontaChipPicker() {
    QuizlerTheme {
        Surface {
            HorizontalChipPicker(
                items = fakeCategoryChips,
                isDropdownExpanded = true,
                chosenItem = fakeCategoryChips.first(),
                onItemClick = {}
            )
        }
    }
}

val fakeCategoryChips = listOf(
    ChosableItem.Content(text = "Muzika", icon = R.drawable.ic_mode_music, itemId = 1.toString()),
    ChosableItem.Content(text = "Film", icon = R.drawable.ic_mode_movie, itemId = 2.toString()),
    ChosableItem.Content(text = "Sport", icon = R.drawable.ic_mode_sport, itemId = 3.toString()),
    ChosableItem.Content(text = "Istorija", icon = R.drawable.ic_mode_history, itemId = 4.toString()),
    ChosableItem.Content(
        text = "Geografija",
        icon = R.drawable.ic_mode_geography,
        itemId = 5.toString()
    ),
    ChosableItem.Content(
        text = "Opsta info.",
        icon = R.drawable.ic_mode_general_knowledge,
        itemId = 6.toString()
    )
)
