package com.devid_academy.common.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devid_academy.gamedata.ApiResult
import com.devid_academy.gamedata.GameDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashViewModel (
    private val gameDataRepository: GameDataRepository,
) : ViewModel() {

    fun getGameData(onFetchComplete: (String?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = gameDataRepository.fetchGamesWithData()
            if (result is ApiResult.Error) {
                onFetchComplete(
                    result.code.toString() + " " +
                    result.exception.message + " " +
                    result.exception.cause.toString())
            } else {
                onFetchComplete(
                    "data fetched"
                )
            }
            Log.e("SplashViewModel", result.toString())
        }
    }
}