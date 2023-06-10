package com.example.quizler

class UnsupportedNavigationRoutingException(
    origin: MainScreen,
    destination: MainScreen
) : Exception("Unsupported routing from ${origin.route} to ${destination.route} ")
