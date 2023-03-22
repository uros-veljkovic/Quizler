package com.example.quizler.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quizler.model.ChosableItem
import com.example.quizler.theme.QuizlerTheme
import com.example.quizler.theme.spaceS

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
            modifier = Modifier
                .clickable {
                    onExpandDropdown()
                }
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Disclaimer(
                modifier = Modifier.wrapContentSize(),
                dividerWidth = 3.dp,
                text = "Izabrana kategorija: ",
                color = MaterialTheme.colorScheme.primary
            )
            Row {
                chosenItem?.icon?.let {
                    Image(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(id = it),
                        contentDescription = null
                    )
                }
                Spacer(modifier = Modifier.size(spaceS))
                chosenItem?.text?.let {
                    val cutText = if (it.count() > 15) it.take(15) + "..." else it
                    Text(text = cutText, overflow = TextOverflow.Ellipsis, maxLines = 1)
                }
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null
                )
            }
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

private val fakeCategoryChips = listOf(
    ChosableItem.Content(
        text = "Opsta Informisanost",
        icon = com.example.quizler.R.drawable.ic_mode_general_knowledge,
        itemId = 6.toString()
    ),
    ChosableItem.Content(text = "Muzika", icon = com.example.quizler.R.drawable.ic_mode_music, itemId = "1"),
    ChosableItem.Content(text = "Film", icon = com.example.quizler.R.drawable.ic_mode_movie, itemId = "2"),
    ChosableItem.Content(text = "Sport", icon = com.example.quizler.R.drawable.ic_mode_sport, itemId = "3"),
    ChosableItem.Content(
        text = "Istorija",
        icon = com.example.quizler.R.drawable.ic_mode_history,
        itemId = "4"
    ),
    ChosableItem.Content(
        text = "Geografija",
        icon = com.example.quizler.R.drawable.ic_mode_geography,
        itemId = "5"
    )
)
