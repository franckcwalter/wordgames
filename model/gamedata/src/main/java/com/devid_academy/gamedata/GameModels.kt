package com.devid_academy.gamedata

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.util.UUID

@Serializable
data class Game(
    val id: Long,
    val name: String,
    val tutorial: List<String>, // TODO : adapt type
    val levels: List<Level> = emptyList(),
    val gameDataHash: String
)

@Serializable
data class Level(
    val id: Long,
    val name: String,
    val datatype: String?, // TODO : adapt type
    val rounds: List<Round> = emptyList()
)

@Serializable
data class Round(
    val id: Long,
    val data: List<String>
)


data class UserRound(
    val id: Long,
    @SerializedName("user_id")
    val userId: UUID,
    @SerializedName("round_id")
    val roundId: Long,
    @SerializedName("datetime")
    val dateTime: LocalDateTime,
    val points: Long
)