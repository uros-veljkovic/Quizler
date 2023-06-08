package com.example.quizler.extensions

import androidx.navigation.NavController

fun NavController.navigateAndForget(route: String) {
    navigate(route) {
        popUpTo(0) {
            inclusive = true
        }
    }
}
