package com.devid_academy.motus

import org.koin.dsl.module

val moduleUiMotus = module {
    single { MotusViewModel(get()) }
}