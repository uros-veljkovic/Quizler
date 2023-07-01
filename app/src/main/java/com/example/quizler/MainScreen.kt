package com.example.quizler

sealed class MainScreen(val route: String) {
    object Empty : MainScreen("empty")
    object SignIn : MainScreen("signIn")
    object CreateProfile : MainScreen("create_profile")
    object Splash : MainScreen("splash")
    object Home : MainScreen("home")
    class Quiz(modeId: String = "{modeId}") : MainScreen("quiz/$modeId")
}

sealed class HomeScreen(val route: String) {
    object Modes : MainScreen("modes")
    object NewQuestion : MainScreen("new_question")
    object Scoreboard : MainScreen("scoreboard")
    object Settings : MainScreen("settings")
}
