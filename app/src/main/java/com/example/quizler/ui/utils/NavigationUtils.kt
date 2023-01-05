package com.example.quizler.ui.utils

import androidx.navigation.NavController
import com.example.quizler.ui.screen.Screen

fun NavController.navigate(origin: Screen, destination: Screen) {
    when (destination) {
        is Screen.Home -> {
            navigate(destination.route) {
                popUpTo(origin.route) {
                    inclusive = true
                }
            }
        }
        is Screen.Quiz -> {
            navigate(destination.route)
        }
        Screen.Scoreboard -> {
            navigate(destination.route) {
                popUpTo(origin.route) {
                    inclusive = true
                }
            }
        }
        else -> throw UnsupportedNavigationRoutingException(origin, destination)
    }
}
