package com.devid_academy.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [
    GameLocal::class,
    LevelLocal::class,
    RoundLocal::class,

], version = 6 )
@TypeConverters(Converters::class)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun gameDataDao(): GameDataDao
}
