package com.devid_academy.ui.model

sealed class Route(val name: String) {
    data object HomeScreen : Route("home")
    data object HangmanScreen : Route("hangman")
    data object MotusScreen : Route("motus")
}