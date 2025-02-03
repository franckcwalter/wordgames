package com.devid_academy.hangman

import org.koin.dsl.module

val moduleUiHangman = module {
    single { HangmanViewModel(get()) }
}