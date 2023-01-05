package com.example.quizler.util.extensions

/**
 * Extension function that capitalizes every word in a string, after which
 * removes blank characters
 */
fun String.capitalizeAndJoinWith(concatenator: String): String {
    return this.split(" ").joinToString(concatenator) { word ->
        word.replaceFirstChar { it.uppercaseChar() }
    }
}

fun String.lowercaseAndJoinWith(concatenator: String): String {
    return this.split(" ").joinToString(concatenator) { word ->
        word.replaceFirstChar { it.lowercaseChar() }
    }
}
