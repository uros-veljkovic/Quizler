package com.example.quizler

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Home : Screen("home")
    object Scoreboard : Screen("scoreboard")
    object NewQuestion : Screen("new_question")
    class Quiz(modeId: String = "{modeId}") : Screen("quiz/$modeId")
}
