package com.devid_academy.common.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devid_academy.auth.ApiResult
import com.devid_academy.auth.UserRepository
import com.devid_academy.ui.GlobalMessageRepository
import com.devid_academy.ui.SharedPrefsManager
import com.devid_academy.ui.SharedPrefsManager.REFRESH_TOKEN
import com.devid_academy.ui.SharedPrefsManager.USER_ID
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class HomeViewModel(
    private val userRepository: UserRepository,
    private val globalMessageRepository: GlobalMessageRepository
) : ViewModel() {


    private val uiState = MutableStateFlow(HomeUiState())
    fun observeUiState(): StateFlow<HomeUiState> = uiState.asStateFlow()

    init {
        val userId: UUID? = SharedPrefsManager.get<String>(USER_ID, null)
            .let { runCatching { UUID.fromString(it) }.getOrNull() }
        Log.e("HomeViewModel","userId : $userId" )
        // on peut utiliser : SharedPrefsManager[USER_ID]
    }
    fun getGameList() {
        // TODO : getGameList with title + image + description
        // Log.e("HomeViewModel getGameList", "gamelist got got")
    }

    fun validateToken() {
        val refreshToken: String = SharedPrefsManager[REFRESH_TOKEN]

        if (refreshToken.isBlank()) {
            uiState.update { it.copy(userIsConnected = false) }
            return
        }

        viewModelScope.launch {
            try {
                val response = userRepository.validateToken(refreshToken)

                if (response is ApiResult.Success) {
                    uiState.update { it.copy(userIsConnected = response.data) }
                }
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error validating refresh token", e)
                uiState.update { it.copy(userIsConnected = false) }
            }
        }
    }

    fun toggleOfflineOnlineMode() {
        // TODO("Not yet implemented")
    }

    fun updateSelectedGame(selectedGameId: String) {
        uiState.update {
            uiState.value.copy(
                selectedGameId = selectedGameId
            )
        }
    }

    fun toggleQuitAppModal() {
        uiState.update {
            uiState.value.copy(
                isDisplayingQuitApp = !uiState.value.isDisplayingQuitApp
            )
        }
    }

    fun sendUserMessage( userMessage: Int) {
        globalMessageRepository.userMessageStringRes.tryEmit(userMessage)
    }

}