package com.devid_academy.common.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devid_academy.gamedata.GameDataRepository
import com.devid_academy.local.LocalDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashViewModel (
    val repo: GameDataRepository,
    val localdb: LocalDatabase
) : ViewModel() {

    fun getGameData() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repo.fetchGamesWithData()
            Log.e("SplashViewModel",result.toString())
        }
    }

}