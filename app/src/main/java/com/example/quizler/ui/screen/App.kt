package com.example.quizler.ui.screen

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.quizler.R
import com.example.quizler.ui.components.BottomNavigation
import com.example.quizler.ui.components.ExitDialog
import com.example.quizler.ui.screen.home.HomeScreen
import com.example.quizler.ui.screen.home.HomeViewModel
import com.example.quizler.ui.screen.quiz.QuizScreen
import com.example.quizler.ui.screen.score.ScoreScreen
import com.example.quizler.ui.screen.score.ScoreViewModel
import com.example.quizler.ui.screen.splash.SplashScreen
import com.example.quizler.ui.utils.disableSplitMotionEvents
import com.example.quizler.ui.utils.navigate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(
    navController: NavHostController = rememberNavController(),
    viewModel: AppViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel = hiltViewModel(),
    scoreViewModel: ScoreViewModel = hiltViewModel()
) {
    // TODO: Add in-app review
    val activity = LocalContext.current as? Activity
    val state by viewModel.state.collectAsState()

    BackHandler(enabled = true) {
        viewModel.handleBack()
    }
    LaunchedEffect(key1 = state.navigate) {
        state.navigate?.let {
            navController.navigate(it.first, it.second)
        }
    }
    LaunchedEffect(key1 = state.shouldCloseApp) {
        if (state.shouldCloseApp) {
            activity?.finish()
        }
    }

    Scaffold(
        modifier = Modifier.disableSplitMotionEvents(),
        bottomBar = {
            AnimatedVisibility(state.bottomNavigationConfig.isBottomNavVisible) {
                BottomNavigation(
                    bottomNavigationConfig = state.bottomNavigationConfig,
                    onSelectedItem = viewModel::setBottomNavItem
                )
            }
        }
    ) { padding ->
        Box {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.default_background_pattern),
                contentScale = ContentScale.FillBounds,
                alpha = 0.2f,
                contentDescription = null
            )
            NavHost(
                modifier = Modifier.background(Color.Transparent),
                navController = navController,
                startDestination = Screen.Splash.route
            ) {
                composable(Screen.Splash.route) {
                    SplashScreen(navController = navController)
                }
                composable(Screen.Home.route) {
                    viewModel.setBottomNavVisible(true)
                    HomeScreen(padding = padding, navController = navController, viewModel = homeViewModel)
                }
                composable(Screen.Scoreboard.route) {
                    viewModel.setBottomNavVisible(true)
                    ScoreScreen(paddingValues = padding, viewModel = scoreViewModel)
                }
                composable(Screen.Quiz().route) {
                    viewModel.setBottomNavVisible(false)
                    it.arguments?.getString("modeId")?.let { modeId ->
                        QuizScreen(
                            padding = padding,
                            navController = navController,
                            modeId = modeId
                        )
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
    }
}
