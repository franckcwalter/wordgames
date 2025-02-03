package com.devid_academy.hangman

import com.devid_academy.gamedata.Round

data class HangmanUiState(
    val wordToDiscover:  List<HangmanLetter> = listOf(),
    val roundList: List<Round> = listOf(),
    val pointsToWin: Long = 10L,
    val userHasWon: Boolean = false,
    val currentRound: Round? = null,
    val userHasLost: Boolean = false
)
