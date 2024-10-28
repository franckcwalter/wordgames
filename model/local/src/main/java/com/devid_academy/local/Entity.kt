package com.devid_academy.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game")
data class GameLocal(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "tutorial") val tutorial: String,
    @ColumnInfo(name = "gameDataHash") val gameDataHash: String
)

@Entity(tableName = "level")
data class LevelLocal(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "datatype") val datatype: String,
    @ColumnInfo(name = "game_id") val gameId: Long
)

@Entity(tableName = "round")
data class RoundLocal(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "data") val data: String,
    @ColumnInfo(name = "level_id") val levelId: Long
)
