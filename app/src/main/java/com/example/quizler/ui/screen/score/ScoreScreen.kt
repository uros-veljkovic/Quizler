package com.example.quizler.ui.screen.score

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.quizler.R
import com.example.quizler.components.BasicDropdownMenu
import com.example.quizler.components.InfoBanner
import com.example.quizler.components.ScoreList
import com.example.quizler.model.ChosableItem
import com.example.quizler.model.Score
import com.example.quizler.theme.QuizlerTheme
import com.example.quizler.theme.spaceM
import com.example.quizler.theme.spaceS
import com.example.quizler.ui.screen.home.plus

@Composable
fun ScoreScreen(
    viewModel: ScoreViewModel
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = Color.Transparent,
        floatingActionButton = {
            FloatingActionButton(onClick = viewModel::refreshScoreboard) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
            }
        },
        snackbarHost = {
            AnimatedVisibility(visible = state.infoBannerData != null) {
                state.infoBannerData?.let {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(start = spaceM, end = spaceM)
                    ) {
                        InfoBanner(data = it, isActionButtonVisible = false)
                    }
                }
            }
        }
    ) {
        ScoreScreenContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(it.plus(spaceS)),
            isDropdownExpanded = state.isDropdownExpanded,
            onExpandDropdown = viewModel::setIsDropdownExpanded,
            chosenMode = state.getChosenMode(),
            isLoading = state.isLoading,
            modes = state.modes,
            onSetChosenMode = viewModel::setChosenMode,
            scores = state.getFilteredScores(),
        )
    }
}

@Composable
fun ScoreScreenContent(
    modifier: Modifier = Modifier,
    isDropdownExpanded: Boolean,
    isLoading: Boolean,
    onExpandDropdown: (Boolean) -> Unit,
    modes: List<ChosableItem>,
    chosenMode: ChosableItem.Content? = null,
    onSetChosenMode: (ChosableItem.Content) -> Unit,
    scores: List<Score>,
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        AnimatedVisibility(
            modifier = modifier, visible = modes.isNotEmpty()
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(spaceS)) {
                chosenMode?.let {
                    BasicDropdownMenu(
                        isExpanded = isDropdownExpanded,
                        onExpandDropdown = onExpandDropdown,
                        chosenItem = it,
                        items = modes,
                        onItemClick = onSetChosenMode
                    )
                }
                if (isLoading) {
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                }
                ScoreList(
                    modifier = Modifier.fillMaxSize(),
                    spaceBetweenItems = spaceS,
                    list = scores
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewScoreScreenContent() {
    QuizlerTheme {
        Scaffold(modifier = Modifier.padding(PaddingValues(spaceM)), floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
            }
        }) { paddingValues ->
            ScoreScreenContent(
                modifier = Modifier.padding(paddingValues),
                isDropdownExpanded = false,
                isLoading = true,
                onExpandDropdown = { },
                chosenMode = ChosableItem.Content(
                    itemId = "b", icon = R.drawable.ic_mode_music, text = "Muzika"
                ),
                modes = chosableItems,
                onSetChosenMode = {},
                scores = mutableListOf<Score>().apply {
                    repeat(10) {
                        add(
                            Score(
                                id = "$it",
                                username = "markoni_123",
                                mode = "a",
                                ranking = 1,
                                score = 12
                            )
                        )
                    }
                }
            )
        }
    }
}

private val chosableItems = listOf(
    ChosableItem.Title(value = R.string.tab_category),
    ChosableItem.Content(
        itemId = "a", icon = R.drawable.ic_mode_sport, text = "Sport"
    ),
    ChosableItem.Content(
        itemId = "b", icon = R.drawable.ic_mode_music, text = "Muzika"
    ),
    ChosableItem.Content(
        itemId = "c", icon = R.drawable.ic_mode_movie, text = "Film"
    ),
    ChosableItem.Content(
        itemId = "d", icon = R.drawable.ic_mode_geography, text = "Geografija"
    ),
    ChosableItem.Content(
        itemId = "e", icon = R.drawable.ic_mode_history, text = "Istorija"
    ),
    ChosableItem.Title(value = R.string.tab_difficulty),
    ChosableItem.Content(
        itemId = "f", icon = R.drawable.ic_mode_easy, text = "Lako"
    ),
    ChosableItem.Content(
        itemId = "g", icon = R.drawable.ic_mode_medium, text = "Srednje"
    ),
    ChosableItem.Content(
        itemId = "h", icon = R.drawable.ic_mode_hard, text = "Tesko"
    ),
    ChosableItem.Title(value = R.string.tab_length),
    ChosableItem.Content(
        itemId = "i", icon = R.drawable.ic_mode_zen, text = "Kratko"
    ),
    ChosableItem.Content(
        itemId = "j", icon = R.drawable.ic_mode_exam, text = "Srednje"
    ),
    ChosableItem.Content(
        itemId = "k", icon = R.drawable.ic_mode_marathon, text = "Dugacko"
    )
)
