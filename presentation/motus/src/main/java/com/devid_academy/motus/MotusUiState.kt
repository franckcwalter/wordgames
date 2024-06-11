package com.devid_academy.motus

data class MotusUiState(
    val grid: MutableList<MutableList<MotusLetter>> = mutableListOf(),

    val currentRow: Int = 0,
    val maxRows: Int = 6,

    val currentMotusLetter: Int = 0,
    val maxMotusLetter: Int = 6,

    val wordToDiscover: List<MotusLetter> = listOf(),
)