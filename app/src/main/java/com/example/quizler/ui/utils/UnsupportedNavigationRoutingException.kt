package com.example.quizler.ui.utils

import com.example.quizler.ui.screen.Screen

class UnsupportedNavigationRoutingException(
    origin: Screen,
    destination: Screen
) : Exception("Unsupported routing from ${origin.route} to ${destination.route} ")
