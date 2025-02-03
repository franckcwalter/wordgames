package com.devid_academy.gamedata.di

import com.devid_academy.gamedata.GameRepository
import com.devid_academy.gamedata.GameRepositoryImpl
import com.devid_academy.gamedata.GameDataService
import org.koin.dsl.module
import retrofit2.Retrofit

inline fun <reified T> createService(retrofit: Retrofit): T {
    return retrofit.create(T::class.java)
}

val moduleModelGamedata = module {
    single { createService<GameDataService>(get()) }
    single<GameRepository> { GameRepositoryImpl(get(), get()) }
}