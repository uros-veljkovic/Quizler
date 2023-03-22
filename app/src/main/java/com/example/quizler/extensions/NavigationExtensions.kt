package com.example.quizler.utils.extensions

import androidx.navigation.NavController
import com.example.quizler.Screen
import com.example.quizler.UnsupportedNavigationRoutingException

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
            navigate(destination.route) {
                popUpTo(origin.route) {
                    inclusive = true
                }
            }
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
