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
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.quizler.HomeScreen
import com.example.quizler.MainScreen
import com.example.quizler.R
import com.example.quizler.components.QuizModes
import com.example.quizler.components.SimpleBottomNavigation
import com.example.quizler.extensions.navigateAndForget
import com.example.quizler.theme.spaceM
import com.example.quizler.theme.spaceS
import com.example.quizler.ui.screen.newquestion.CreateNewQuestionScreen
import com.example.quizler.ui.screen.newquestion.CreateNewQuestionViewModel
import com.example.quizler.ui.screen.score.ScoreScreen
import com.example.quizler.ui.screen.score.ScoreViewModel
import com.example.quizler.ui.screen.settings.SettingsScreen
import com.example.quizler.ui.screen.settings.SettingsViewModel

@Composable
fun HomeScreen(
    modesViewModel: ModesViewModel,
    createNewQuestionViewModel: CreateNewQuestionViewModel,
    scoreViewModel: ScoreViewModel,
    settingsViewModel: SettingsViewModel,
    parentNavController: NavHostController
) {
    val navHostController = rememberNavController()

    Scaffold(
        bottomBar = {
            SimpleBottomNavigation(navController = navHostController)
        },
        content = { padding ->
            NavHost(navController = navHostController, startDestination = HomeScreen.Modes.route) {
                composable(HomeScreen.Modes.route) {
                    ModesScreen(
                        modifier = Modifier.padding(paddingValues = padding + spaceS),
                        viewModel = modesViewModel,
                        navController = parentNavController
                    )
                }
                composable(HomeScreen.NewQuestion.route) {
                    CreateNewQuestionScreen(
                        modifier = Modifier.padding(paddingValues = padding),
                        viewModel = createNewQuestionViewModel
                    )
                }
                composable(HomeScreen.Scoreboard.route) {
                    ScoreScreen(
                        modifier = Modifier.padding(paddingValues = padding),
                        viewModel = scoreViewModel
                    )
                }
                composable(HomeScreen.Settings.route) {
                    SettingsScreen(modifier = Modifier.padding(padding + spaceM))
                }
            }
        }
    )
}

@Composable
private fun ModesScreen(
    modifier: Modifier = Modifier,
    viewModel: ModesViewModel,
    navController: NavController
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
        QuizModes(
            cardDescription = stringResource(id = R.string.mode_category_description),
            cardTitle = stringResource(id = R.string.tab_category),
            state = state.categories,
            onModeSelected = { navController.navigateAndForget(MainScreen.Quiz(it.id).route) }
        )
        Spacer(modifier = Modifier.size(spaceM))
        QuizModes(
            cardDescription = stringResource(id = R.string.modes_difficulty_description),
            cardTitle = stringResource(id = R.string.tab_difficulty),
            state = state.difficulties,
            onModeSelected = { navController.navigateAndForget(MainScreen.Quiz(it.id).route) }
        )
        Spacer(modifier = Modifier.size(spaceM))
        QuizModes(
            cardDescription = stringResource(id = R.string.modes_length_description),
            cardTitle = stringResource(id = R.string.tab_length),
            state = state.length,
            onModeSelected = { navController.navigateAndForget(MainScreen.Quiz(it.id).route) }
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
