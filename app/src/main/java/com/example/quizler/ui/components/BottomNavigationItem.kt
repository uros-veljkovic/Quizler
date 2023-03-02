package com.example.quizler.ui.components

import androidx.compose.material.icons.filled.Home
import com.example.quizler.R
import com.example.quizler.ui.screen.Screen

enum class BottomNavigationItem(val route: String, val icon: Int, val titleResId: Int) {
    Home(
        route = Screen.Home.route,
        icon = R.drawable.ic_home,
        titleResId = R.string.home
    ),
    NewQuestion(
        route = Screen.NewQuestion.route,
        icon = R.drawable.ic_add,
        titleResId = R.string.new_question
    ),
    Scoreboard(
        route = Screen.Scoreboard.route,
        icon = R.drawable.ic_scoreboard,
        titleResId = R.string.scoreboard
    )
}
