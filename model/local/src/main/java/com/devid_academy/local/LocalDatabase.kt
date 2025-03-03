package com.devid_academy.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.devid_academy.local.game.GameDataDao
import com.devid_academy.local.game.GameLocal
import com.devid_academy.local.game.LevelLocal
import com.devid_academy.local.game.RoundLocal
import com.devid_academy.local.game.UserRoundLocal
import com.devid_academy.local.user.UserDao
import com.devid_academy.local.user.UserLocal

@Database(entities = [
    GameLocal::class,
    LevelLocal::class,
    RoundLocal::class,
    UserLocal::class,
    UserRoundLocal::class,
], version = 19)
@TypeConverters(Converters::class)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun gameDataDao(): GameDataDao
    abstract fun userDao(): UserDao
}
