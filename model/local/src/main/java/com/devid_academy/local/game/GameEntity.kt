package com.devid_academy.local.game

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.util.UUID


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

@Entity(
    // finalemnt j'ai ajouté un id
    tableName = "user_round",
    //primaryKeys = ["user_id", "round_id"]
)
data class UserRoundLocal(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "user_id") val userId: UUID,
    @ColumnInfo(name = "round_id") val roundId: Long,
    @ColumnInfo(name = "datetime") val datetime: LocalDateTime,
    @ColumnInfo(name = "points") val points: Long
)

