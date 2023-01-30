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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.quizler.R
import com.example.quizler.ui.components.BasicDropdownMenu
import com.example.quizler.ui.components.InfoBanner
import com.example.quizler.ui.components.ScoreList
import com.example.quizler.ui.model.DropdownItem
import com.example.quizler.ui.model.Score
import com.example.quizler.ui.screen.home.plus
import com.example.quizler.ui.theme.QuizlerTheme
import com.example.quizler.ui.theme.spaceM
import com.example.quizler.ui.theme.spaceS

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScoreScreen(
    paddingValues: PaddingValues,
    viewModel: ScoreViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()

    Scaffold(
        modifier = Modifier.padding(paddingValues),
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
    modes: List<DropdownItem>,
    chosenMode: DropdownItem.Content? = null,
    onSetChosenMode: (DropdownItem.Content) -> Unit,
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

@OptIn(ExperimentalMaterial3Api::class)
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
                chosenMode = DropdownItem.Content(
                    itemId = "b", icon = R.drawable.ic_mode_music, text = "Muzika"
                ),
                modes = dropdownItems,
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

private val dropdownItems = listOf(
    DropdownItem.Title(value = R.string.tab_category),
    DropdownItem.Content(
        itemId = "a", icon = R.drawable.ic_mode_sport, text = "Sport"
    ),
    DropdownItem.Content(
        itemId = "b", icon = R.drawable.ic_mode_music, text = "Muzika"
    ),
    DropdownItem.Content(
        itemId = "c", icon = R.drawable.ic_mode_movie, text = "Film"
    ),
    DropdownItem.Content(
        itemId = "d", icon = R.drawable.ic_mode_geography, text = "Geografija"
    ),
    DropdownItem.Content(
        itemId = "e", icon = R.drawable.ic_mode_history, text = "Istorija"
    ),
    DropdownItem.Title(value = R.string.tab_difficulty),
    DropdownItem.Content(
        itemId = "f", icon = R.drawable.ic_mode_easy, text = "Lako"
    ),
    DropdownItem.Content(
        itemId = "g", icon = R.drawable.ic_mode_medium, text = "Srednje"
    ),
    DropdownItem.Content(
        itemId = "h", icon = R.drawable.ic_mode_hard, text = "Tesko"
    ),
    DropdownItem.Title(value = R.string.tab_length),
    DropdownItem.Content(
        itemId = "i", icon = R.drawable.ic_mode_zen, text = "Kratko"
    ),
    DropdownItem.Content(
        itemId = "j", icon = R.drawable.ic_mode_exam, text = "Srednje"
    ),
    DropdownItem.Content(
        itemId = "k", icon = R.drawable.ic_mode_marathon, text = "Dugacko"
    )
)
