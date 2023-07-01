package com.example.quizler.model

import com.example.quizler.HomeScreen
import com.example.quizler.R

enum class BottomNavigationItem(val route: String, val icon: Int, val titleResId: Int) {
    Home(
        route = HomeScreen.Modes.route,
        icon = R.drawable.ic_home,
        titleResId = R.string.home
    ),
    NewQuestion(
        route = HomeScreen.NewQuestion.route,
        icon = R.drawable.ic_add,
        titleResId = R.string.new_question
    ),
    Scoreboard(
        route = HomeScreen.Scoreboard.route,
        icon = R.drawable.ic_scoreboard,
        titleResId = R.string.scoreboard
    ),
    Settings(
        route = HomeScreen.Settings.route,
        icon = R.drawable.ic_settings,
        titleResId = R.string.settings
    )
}
