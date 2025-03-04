package com.devid_academy.ui

sealed class Route(val name: String) {
    data object SplashScreen : Route("splash")
    data object HomeScreen : Route("home")
    data object HangmanScreen : Route("hangman")
    data object MotusScreen : Route("motus")
    data object LoginScreen : Route("login")
    data object SignupScreen : Route("singup")
}