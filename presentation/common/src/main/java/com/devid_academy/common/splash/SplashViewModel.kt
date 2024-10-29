package com.devid_academy.common.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devid_academy.gamedata.GameDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashViewModel (
    val gameDataRepository: GameDataRepository,
) : ViewModel() {

    fun getGameData() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = gameDataRepository.fetchGamesWithData()
            Log.e("SplashViewModel", result.toString())
        }
    }

}