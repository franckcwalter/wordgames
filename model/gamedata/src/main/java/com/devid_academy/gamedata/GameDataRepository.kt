package com.devid_academy.gamedata

import android.util.Log
import com.devid_academy.local.GameLocal
import com.devid_academy.local.LevelLocal
import com.devid_academy.local.LocalDatabase
import com.devid_academy.local.RoundLocal
import retrofit2.HttpException

interface GameDataRepository {
    suspend fun fetchGamesWithData(): ApiResult<List<Game>>
    suspend fun insertGamesWithDataIntoLocalDb(gamesWithData: List<Game>)
    suspend fun getRoundsByGameAndLevel(gameName: String, levelName: String): List<Round>
}

class GameDataRepositoryImpl(
    private val apiService: GameDataService,
    private val localdb: LocalDatabase
) : GameDataRepository {

    override suspend fun fetchGamesWithData(): ApiResult<List<Game>> {
        return try {
            val response = handleApi { apiService.fetchGamesWithData() }
            Log.e("GameDataRepositoryImpl","fetchGamesWithData() response : $response")

            if (response is ApiResult.Success) {
                insertGamesWithDataIntoLocalDb(response.data)
            }
            response
        } catch (e: HttpException) {
            ApiResult.Error(e, e.code())
        } catch (e: Throwable) {
            ApiResult.Error(e)
        }
    }

    override suspend fun insertGamesWithDataIntoLocalDb(gamesWithData: List<Game>) {
        gamesWithData.forEach { game ->

            Log.d(
                "GameDataRepositoryImpl gamesWithData ",
                "gameId : ${game.id} " +
                        "gameDataHash : ${game.gameDataHash} gameExistsWithSameHash :"  +
                         localdb.gameDataDao().gameWithSameHashExists(game.id, game.gameDataHash).toString())

            if (localdb.gameDataDao().gameWithSameHashExists(game.id, game.gameDataHash) == 0) {

                Log.d("GameDataRepositoryImpl","game data is updated ")

                localdb.gameDataDao().insertOrUpdateGame(mapGameToGameLocal(game))

                localdb.gameDataDao().insertAllLevels(
                    game.levels.map { mapLevelToLevelLocal(it, game.id) }
                )
                localdb.gameDataDao().insertAllRounds(
                    game.levels.flatMap { level ->
                        level.rounds.map { mapRoundToRoundLocal(it, level.id) }
                    }
                )

            }
        }
    }

    override suspend fun getRoundsByGameAndLevel(gameName: String, levelName: String): List<Round> {
        return localdb.gameDataDao().getRoundsByGameAndLevel(gameName, levelName)
            .map { mapRoundLocalToRound(it) }
    }


    /***** MAPPERS *****/

    private fun mapRoundLocalToRound(roundLocal: RoundLocal): Round {
        return Round(
            id = roundLocal.id,
            data = roundLocal.data.split(",").map { it.trim() }
        )
    }

    private fun mapGameToGameLocal(game: Game): GameLocal {
        return GameLocal(
            id = game.id,
            name = game.name,
            tutorial = game.tutorial.joinToString(separator = ","),
            gameDataHash = game.gameDataHash
        )
    }

    private fun mapLevelToLevelLocal(level: Level, gameId: Long): LevelLocal {
        return LevelLocal(
            id = level.id,
            name = level.name,
            datatype = level.datatype ?: "",
            gameId = gameId
        )
    }

    private fun mapRoundToRoundLocal(round: Round, levelId: Long): RoundLocal {
        return RoundLocal(
            id = round.id,
            data = round.data.joinToString(separator = ","),
            levelId = levelId
        )
    }
}