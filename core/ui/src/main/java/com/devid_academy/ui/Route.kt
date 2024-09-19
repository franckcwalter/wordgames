package com.devid_academy.ui.model

sealed class Route(val name: String) {
    data object SplashScreen : Route("splash")
    data object HomeScreen : Route("home")
    data object HangmanScreen : Route("hangman")
    data object MotusScreen : Route("motus")
}