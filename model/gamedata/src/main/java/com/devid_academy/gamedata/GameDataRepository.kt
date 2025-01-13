package com.devid_academy.gamedata

import android.util.Log
import com.devid_academy.local.game.GameLocal
import com.devid_academy.local.game.LevelLocal
import com.devid_academy.local.LocalDatabase
import com.devid_academy.local.game.RoundLocal
import com.devid_academy.local.game.UserRoundLocal
import com.devid_academy.distant.ApiResult
import com.devid_academy.distant.handleApi
import retrofit2.HttpException
import java.time.LocalDateTime
import java.util.UUID

interface GameDataRepository {
    suspend fun fetchGamesWithData(): ApiResult<List<Game>>
    suspend fun insertGamesWithDataIntoLocalDb(gamesWithData: List<Game>)
    suspend fun getRoundsByGameAndLevel(gameName: String, levelName: String): List<Round>
    suspend fun insertFinishedRound(roundId: Long, points: Long, userId: UUID)
    suspend fun getTotalPoints(): Long
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

    override suspend fun insertFinishedRound(roundId: Long, points: Long, userId: UUID){
        localdb.gameDataDao().insertFinishedRound(
            UserRoundLocal(
                id = 0,
                userId = userId,
                datetime = LocalDateTime.now(),
                points = points,
                roundId = roundId
            )
        )
    }

    override suspend fun getTotalPoints(): Long {
        return localdb.gameDataDao().getTotalPoints()
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