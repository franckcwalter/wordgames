package com.devid_academy.ui

import org.koin.dsl.module

val moduleCore = module {
    single { GlobalMessageRepository() }
}