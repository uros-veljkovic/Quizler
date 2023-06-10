package com.example.quizler.ui.screen.onboarding.empty

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.quizler.extensions.navigateAndForget

@Composable
fun EmptyScreen(navController: NavController, viewModel: EmptyViewModel) {
    val nextScreen by viewModel.nextScreen.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = nextScreen) {
        if (nextScreen.isNotEmpty()) {
            navController.navigateAndForget(nextScreen)
        }
    }
}
