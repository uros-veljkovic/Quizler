package com.example.quizler.ui.screen

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Home : Screen("home")
    object Scoreboard : Screen("scoreboard")
    object NewQuestion : Screen("new_question")
    class Quiz(val modeId: String = "{modeId}") : Screen("quiz/$modeId")
}
