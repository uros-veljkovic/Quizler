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
import com.example.quizler.MainScreen
import com.example.quizler.components.ExitDialog
import com.example.quizler.extensions.disableSplitMotionEvents
import com.example.quizler.ui.screen.home.HomeScreen
import com.example.quizler.ui.screen.home.ModesViewModel
import com.example.quizler.ui.screen.newquestion.CreateNewQuestionViewModel
import com.example.quizler.ui.screen.onboarding.CreateProfileScreen
import com.example.quizler.ui.screen.onboarding.empty.EmptyScreen
import com.example.quizler.ui.screen.onboarding.signin.SignInScreen
import com.example.quizler.ui.screen.onboarding.signin.SignInViewModel
import com.example.quizler.ui.screen.onboarding.splash.SplashScreen
import com.example.quizler.ui.screen.onboarding.splash.SplashViewModel
import com.example.quizler.ui.screen.quiz.QuizScreen
import com.example.quizler.ui.screen.score.ScoreViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun App(
    navController: NavHostController = rememberNavController(),
    modesViewModel: ModesViewModel = koinViewModel(),
    createNewQuestionViewModel: CreateNewQuestionViewModel = koinViewModel(),
    scoreViewModel: ScoreViewModel = koinViewModel(),
    signInViewModel: SignInViewModel = koinViewModel(),
    splashViewModel: SplashViewModel = koinViewModel(),
    viewModel: AppViewModel = koinViewModel(),
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
    ) { padding ->
        NavHost(
            modifier = Modifier
                .background(Color.Transparent)
                .padding(padding),
            navController = navController,
            startDestination = MainScreen.Empty.route
        ) {
            composable(MainScreen.Empty.route) {
                EmptyScreen(navController = navController)
            }
            composable(MainScreen.SignIn.route) {
                SignInScreen(navController = navController, viewModel = signInViewModel)
            }
            composable(MainScreen.CreateProfile.route) {
                CreateProfileScreen(navController = navController)
            }
            composable(MainScreen.Splash.route) {
                SplashScreen(navController = navController, viewModel = splashViewModel)
            }
            composable(MainScreen.Home.route) {
                HomeScreen(modesViewModel, createNewQuestionViewModel, scoreViewModel, navController)
            }
            composable(MainScreen.Quiz().route) {
                it.arguments?.getString("modeId")?.let { modeId ->
                    QuizScreen(navController = navController, modeId = modeId)
                }
            }
        }
    }
    AnimatedVisibility(
        visible = state.isExitDialogVisible, enter = fadeIn(tween(200)), exit = fadeOut(tween(200))
    ) {
        ExitDialog(
            onConfirm = viewModel::closeApp, onDecline = viewModel::handleExitDialogDecline
        )
    }
}
