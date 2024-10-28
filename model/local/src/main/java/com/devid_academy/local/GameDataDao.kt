package com.devid_academy.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GameDataDao {


    @Insert
    suspend fun insertGames(game: GameLocal)


    @Insert
    suspend fun insertLevel(game: LevelLocal)

    @Insert
    suspend fun insertRound(game: RoundLocal)



    @Query("SELECT COUNT(*) FROM game WHERE id = :gameId AND gameDataHash = :hash")
    suspend fun gameWithSameHashExists(gameId: Long, hash: String): Int

    @Query("""
    SELECT round.id, round.data, round.level_id FROM round
    INNER JOIN level ON round.level_id = level.id
    INNER JOIN game ON level.game_id = game.id
    WHERE game.name = :gameName AND level.name = :levelName
""")
    suspend fun getRoundsByGameAndLevel(gameName: String, levelName: String): List<RoundLocal>


}
