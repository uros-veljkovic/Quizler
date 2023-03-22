package com.example.quizler.ui.screen

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.quizler.Screen
import com.example.quizler.components.ExitDialog
import com.example.quizler.components.SimpleBottomNavigation
import com.example.quizler.extensions.disableSplitMotionEvents
import com.example.quizler.ui.screen.home.HomeScreen
import com.example.quizler.ui.screen.home.HomeViewModel
import com.example.quizler.ui.screen.newquestion.CreateNewQuestionScreen
import com.example.quizler.ui.screen.newquestion.CreateNewQuestionViewModel
import com.example.quizler.ui.screen.quiz.QuizScreen
import com.example.quizler.ui.screen.score.ScoreScreen
import com.example.quizler.ui.screen.score.ScoreViewModel
import com.example.quizler.ui.screen.splash.SplashScreen
import com.example.quizler.ui.screen.splash.SplashViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun App(
    navController: NavHostController = rememberNavController(),
    homeViewModel: HomeViewModel = koinViewModel(),
    splashViewModel: SplashViewModel = koinViewModel(),
    viewModel: AppViewModel = koinViewModel(),
    scoreViewModel: ScoreViewModel = koinViewModel(),
    newQuestionViewModel: CreateNewQuestionViewModel = koinViewModel()
) {
    // TODO: Add in-app review
    val activity = LocalContext.current as? Activity
    val state by viewModel.state.collectAsStateWithLifecycle()

    BackHandler(enabled = true) {
        viewModel.handleBack()
    }
    LaunchedEffect(key1 = state.shouldCloseApp) {
        if (state.shouldCloseApp) {
            activity?.finish()
        }
    }
    Scaffold(
        modifier = Modifier.disableSplitMotionEvents(),
        bottomBar = {
            AnimatedVisibility(visible = state.bottomNavigationConfig.isBottomNavVisible) {
                SimpleBottomNavigation(navController = navController)
            }
        }
    ) { padding ->
        NavHost(
            modifier = Modifier
                .background(Color.Transparent)
                .padding(padding),
            navController = navController,
            startDestination = Screen.Splash.route
        ) {
            composable(Screen.Splash.route) {
                SplashScreen(navController = navController, viewModel = splashViewModel)
            }
            composable(Screen.Home.route) {
                viewModel.setBottomNavVisible(true)
                HomeScreen(navController = navController, viewModel = homeViewModel)
            }
            composable(Screen.Scoreboard.route) {
                viewModel.setBottomNavVisible(true)
                ScoreScreen(viewModel = scoreViewModel)
            }
            composable(Screen.NewQuestion.route) {
                viewModel.setBottomNavVisible(true)
                CreateNewQuestionScreen(viewModel = newQuestionViewModel)
            }
            composable(Screen.Quiz().route) {
                viewModel.setBottomNavVisible(false)
                it.arguments?.getString("modeId")?.let { modeId ->
                    QuizScreen(navController = navController, modeId = modeId)
                }
            }
        }
    }
    AnimatedVisibility(
        visible = state.isExitDialogVisible,
        enter = fadeIn(tween(200)),
        exit = fadeOut(tween(200))
    ) {
        ExitDialog(
            onConfirm = viewModel::closeApp,
            onDecline = viewModel::handleExitDialogDecline
        )
    }
}
