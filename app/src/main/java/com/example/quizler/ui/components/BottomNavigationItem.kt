package com.example.quizler.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.quizler.R
import com.example.quizler.ui.screen.Screen

enum class BottomNavigationItem(val route: String, val icon: ImageVector, val titleResId: Int) {
    Home(
        route = Screen.Home.route,
        icon = Icons.Default.Home,
        titleResId = R.string.home
    ),
    NewQuestion(
        route = Screen.NewQuestion.route,
        icon = Icons.Default.Add,
        titleResId = R.string.new_question
    ),
    Scoreboard(
        route = Screen.Scoreboard.route,
        icon = Icons.Default.List,
        titleResId = R.string.scoreboard
    )
}
