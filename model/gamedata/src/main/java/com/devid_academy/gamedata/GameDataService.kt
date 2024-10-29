package com.devid_academy.gamedata

import retrofit2.Response
import retrofit2.http.GET

interface GameDataService {

    @GET("/games/with-data")
    suspend fun fetchGamesWithData(): Response<List<Game>>

}

