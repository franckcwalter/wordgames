package com.devid_academy.hangman

import com.devid_academy.gamedata.Round
import com.devid_academy.ui.LevelEnum

data class HangmanUiState(
    val level: LevelEnum = LevelEnum.EASY,
    val wordToDiscover:  List<HangmanLetter> = listOf(),
    val roundList: List<Round> = listOf(),
    val pointsToWin: Long = 10L,
    val userHasWon: Boolean = false,
    val currentRound: Round? = null,
    val userHasLost: Boolean = false
)
