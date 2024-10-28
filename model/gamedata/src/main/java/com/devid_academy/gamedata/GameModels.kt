package com.devid_academy.gamedata

import kotlinx.serialization.Serializable

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
