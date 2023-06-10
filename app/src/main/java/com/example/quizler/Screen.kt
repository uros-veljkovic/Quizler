package com.example.quizler

sealed class Screen(val route: String) {
    object Empty : Screen("empty")
    object SignIn : Screen("signIn")
    object Splash : Screen("splash")
    object Onboarding : Screen("onboarding")
    object Home : Screen("home")
    object Scoreboard : Screen("scoreboard")
    object NewQuestion : Screen("new_question")
    class Quiz(modeId: String = "{modeId}") : Screen("quiz/$modeId")
}
