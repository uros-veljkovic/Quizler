package com.example.quizler

class UnsupportedNavigationRoutingException(
    origin: Screen,
    destination: Screen
) : Exception("Unsupported routing from ${origin.route} to ${destination.route} ")
