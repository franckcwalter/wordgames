package com.devid_academy.common.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devid_academy.auth.CreateUserGuestDto
import com.devid_academy.auth.UserRepository
import com.devid_academy.distant.ApiResult
import com.devid_academy.gamedata.GameRepository
import com.devid_academy.local.RoleEnum
import com.devid_academy.local.user.UserLocal
import com.devid_academy.ui.SharedPrefsManager
import com.devid_academy.ui.SharedPrefsManager.USER_ID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.UUID


class SplashViewModel (
    private val gameRepository: GameRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    init {
        createGuestUserIfNotExists()
    }

    private fun createGuestUserIfNotExists() {
        viewModelScope.launch(Dispatchers.IO) {
            // TODO : maybe check in sharedPrefs instead of lcoaldb ?
            if (!userRepository.checkIfUserExistsLocal()){

                val resultDistant = userRepository.createGuestUserRemote(
                    CreateUserGuestDto(
                        id = UUID.randomUUID(),
                        creationDateTime = LocalDateTime.now(),
                    )
                )

                when(resultDistant){
                    is ApiResult.Success -> {
                        Log.e("SplashViewModel", "resultDistant.data: ${resultDistant.data}")

                        val resultLocal = userRepository.createGuestUserLocal(
                            UserLocal(
                                id = resultDistant.data.id,
                                creationDatetime = resultDistant.data.creationDatetime,
                                role = RoleEnum.USER
                            )
                        )

                        if (resultLocal >= 0){
                            Log.e("SplashViewModel", resultLocal.toString() )
                            SharedPrefsManager[USER_ID] = resultDistant.data.id
                        }
                    }
                    is ApiResult.Error -> {
                        // TODO handle case when user id is already used ;
                        Log.e("SplashViewModel", "${resultDistant.exception}")
                    }
                }
            }
        }
    }

    fun getGameData(onFetchComplete: (String?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = gameRepository.fetchGamesWithData()
            if (result is ApiResult.Error) {
                onFetchComplete(
                    result.httpCode.toString() + " " +
                    result.exception.message + " " +
                    result.exception.cause.toString())
            } else {
                onFetchComplete(
                    "game data fetched"
                )
            }
        }
    }
}