package com.devid_academy.hangman

data class HangmanUiState(
    /*val  keyboardLetterList : MutableList<List<Key>> = mutableListOf(
        listOf(Key('a'), Key('z'), Key('e'), Key('r'), Key('t'), Key('y'), Key('u'), Key('i'), Key('o'), Key('p')),
        listOf(Key('q'), Key('s'), Key('d'), Key('f'), Key('g'), Key('h'), Key('j'), Key('k'), Key('l'), Key('m')),
        listOf(Key('w'), Key('x'), Key('c'), Key('v'), Key('b'), Key('n'))
    ),*/
    val wordToDiscover:  List<HangmanLetter> = listOf(),
    val counter: Int = 10
)


