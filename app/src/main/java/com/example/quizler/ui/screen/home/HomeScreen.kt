package com.example.quizler.ui.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.quizler.R
import com.example.quizler.ui.components.QuizModes
import com.example.quizler.ui.screen.Screen
import com.example.quizler.ui.theme.spaceM
import com.example.quizler.ui.utils.navigate

@Composable
fun HomeScreen(
    navController: NavController = rememberNavController(),
    viewModel: HomeViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .padding(spaceM)
            .verticalScroll(rememberScrollState())
    ) {
        QuizModes(
            cardDescription = stringResource(id = R.string.mode_category_description),
            cardTitle = stringResource(id = R.string.tab_category),
            state = state.categories,
            onModeSelected = { navController.navigate(Screen.Home, Screen.Quiz(it.id)) }
        )
        Spacer(modifier = Modifier.size(spaceM))
        QuizModes(
            cardDescription = stringResource(id = R.string.modes_difficulty_description),
            cardTitle = stringResource(id = R.string.tab_difficulty),
            state = state.difficulties,
            onModeSelected = { navController.navigate(Screen.Home, Screen.Quiz(it.id)) }
        )
        Spacer(modifier = Modifier.size(spaceM))
        QuizModes(
            cardDescription = stringResource(id = R.string.modes_length_description),
            cardTitle = stringResource(id = R.string.tab_length),
            state = state.length,
            onModeSelected = { navController.navigate(Screen.Home, Screen.Quiz(it.id)) }
        )
    }
}

operator fun PaddingValues.plus(padding: Dp): PaddingValues {
    return PaddingValues(
        start = this.calculateStartPadding(LayoutDirection.Ltr) + padding,
        end = calculateEndPadding(LayoutDirection.Rtl) + padding,
        top = calculateTopPadding() + padding,
        bottom = calculateBottomPadding() + padding
    )
}
