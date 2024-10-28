package com.devid_academy.local

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val moduleModelLocalDB = module {
    single <LocalDatabase>{
        Room.databaseBuilder(
            androidContext(),
            LocalDatabase::class.java, "wordgames-localdb"
        ).fallbackToDestructiveMigration()
            .build()
    }
}