package com.devid_academy.gamedata

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface GameDataService {

    @GET("/games/with-data")
    suspend fun fetchGamesWithData(): Response<List<Game>>

    @POST("rounds/finished")
    suspend fun postFinishedRounds(
        @Body finishedRounds: List<UserRound>
    ): Response<Unit>

}

