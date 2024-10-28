package com.devid_academy.common.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel : ViewModel() {


    private val uiState = MutableStateFlow(HomeUiState())
    fun observeUiState(): StateFlow<HomeUiState> = uiState.asStateFlow()

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