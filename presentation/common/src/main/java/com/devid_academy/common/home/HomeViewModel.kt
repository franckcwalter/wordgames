package com.devid_academy.common.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.devid_academy.ui.SharedPrefsManager
import com.devid_academy.ui.SharedPrefsManager.USER_ID
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

class HomeViewModel : ViewModel() {


    private val uiState = MutableStateFlow(HomeUiState())
    fun observeUiState(): StateFlow<HomeUiState> = uiState.asStateFlow()

    init {
        val userId : UUID = SharedPrefsManager[USER_ID]
        Log.e("HomeViewModel","userId : ${userId}" )
    }
    fun getGameList() {
        // TODO : getGameList with title + image + description
        // Log.e("HomeViewModel getGameList", "gamelist got got")
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

}