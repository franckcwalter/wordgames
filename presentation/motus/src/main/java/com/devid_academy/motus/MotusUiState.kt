package com.devid_academy.motus

import com.devid_academy.gamedata.LevelEnum
import com.devid_academy.gamedata.Round

data class MotusUiState(

    val level: LevelEnum = LevelEnum.EASY,
    val wordList: List<Round> = listOf(),

    val grid: MutableList<MutableList<MotusLetter>> = mutableListOf(),

    val currentRow: Int = 0,
    val maxRows: Int = 6,

    val currentMotusLetter: Int = 0,
    val maxMotusLetter: Int = 6,

    val wordToDiscover: List<MotusLetter> = listOf()

)