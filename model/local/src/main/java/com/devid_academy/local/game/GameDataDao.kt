package com.devid_academy.local.game

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GameDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateGame(game: GameLocal)

    @Query("SELECT COUNT(*) FROM game WHERE id = :gameId AND gameDataHash = :hash")
    suspend fun gameWithSameHashExists(gameId: Long, hash: String): Int


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllLevels(levels: List<LevelLocal>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllRounds(rounds: List<RoundLocal>)


    @Query("""
    SELECT round.id, round.data, round.level_id FROM round
    INNER JOIN level ON round.level_id = level.id
    INNER JOIN game ON level.game_id = game.id
    WHERE game.name = :gameName AND level.name = :levelName
    """)
    suspend fun getRoundsByGameAndLevel(gameName: String, levelName: String): List<RoundLocal>


    @Query("""
    SELECT selected_level FROM game
    WHERE name = :gameName
    """)
    suspend fun getSelectedLevelForGame(gameName: String): String

    @Insert(onConflict = OnConflictStrategy.IGNORE) // TODO : see if conflict strategy is relevant
    suspend fun insertFinishedRound(userRound: UserRoundLocal)

    @Query("SELECT * FROM user_round")
    suspend fun getAllUserRounds(): List<UserRoundLocal>

    @Query("""
    SELECT SUM(points) 
    FROM user_round
    """)
    suspend fun getTotalPoints(): Long

    @Query("""
    UPDATE game
    SET selected_level = :level
    WHERE name = :gameName
    """)
    suspend fun setSelectedLevelForGame(gameName: String, level: String)

}
