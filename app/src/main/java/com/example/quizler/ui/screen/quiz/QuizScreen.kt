package com.example.quizler.ui.screen.quiz

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.quizler.R
import com.example.quizler.ui.components.BasicDialog
import com.example.quizler.ui.components.OptionsPickerDialog
import com.example.quizler.ui.components.ResultInfoDialog
import com.example.quizler.ui.components.quiz.QuestionScreenContent
import com.example.quizler.ui.model.InfoBannerData
import com.example.quizler.ui.screen.Screen
import com.example.quizler.ui.utils.navigate

@Composable
fun QuizScreen(
    navController: NavController = rememberNavController(),
    viewModel: QuizViewModel = hiltViewModel(),
    modeId: String
) {

    BackHandler(enabled = true) {
        viewModel.onBackPressed()
    }

    val mode by remember { mutableStateOf(modeId) }
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.startQuiz(mode)
    }
    LaunchedEffect(key1 = state.shouldExitQuiz) {
        if (state.shouldExitQuiz) {
            navController.navigate(Screen.Quiz(), Screen.Home)
        }
    }

    AnimatedVisibility(
        visible = state.question != null,
        enter = fadeIn(tween(1000)),
        exit = fadeOut(tween(1000))
    ) {
        state.question?.let {
            QuestionScreenContent(
                state,
                viewModel::answered,
                viewModel::reportQuestion
            )
        }
    }
    AnimatedVisibility(
        visible = state.result != null,
        enter = fadeIn(tween(200)),
        exit = fadeOut(tween(200))
    ) {
        state.result?.let {
            ResultInfoDialog(
                resultInfoData = it,
                username = state.username,
                shouldSaveUsername = state.shouldSaveUsername,
                onUsernameChange = viewModel::onUsernameChange,
                onSaveUsernameCheckBoxChecked = viewModel::onSaveUsername,
                onGoToHomeScreenClick = viewModel::endQuiz
            )
        }
    }
    AnimatedVisibility(
        visible = state.isReportQuestionDialogVisible,
        enter = fadeIn(tween(200)),
        exit = fadeOut(tween(200))
    ) {
        OptionsPickerDialog(
            data = InfoBannerData.ReportQuestion,
            items = state.reportTypes,
            onChooseItem = viewModel::onReportItemChosen,
            onConfirm = viewModel::onConfirmReportQuestion
        )
    }
    AnimatedVisibility(
        visible = state.isExitDialogVisible, enter = fadeIn(tween(200)), exit = fadeOut(tween(200))
    ) {
        BasicDialog(
            icon = R.drawable.ic_exit,
            title = stringResource(id = R.string.dialog_exit_quiz_title),
            description = stringResource(id = R.string.dialog_exit_quiz_description),
            positiveButtonText = stringResource(id = R.string.yes),
            onPositiveButtonClick = viewModel::exitQuiz,
            negativeButtonText = stringResource(id = R.string.no),
            onNegativeButtonClick = viewModel::onBackPressed
        )
    }
}
