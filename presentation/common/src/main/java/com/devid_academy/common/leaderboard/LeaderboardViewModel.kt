package com.devid_academy.common.leaderboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.devid_academy.distant.ApiResult
import com.devid_academy.gamedata.GameRepository
import com.devid_academy.ui.GlobalMessageRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LeaderboardViewModel(
    private val gameRepository: GameRepository,
    private val globalMessageRepository: GlobalMessageRepository
): ViewModel() {


    private val uiState = MutableStateFlow(LeaderboardUiState())
    fun observeUiState(): StateFlow<LeaderboardUiState> = uiState.asStateFlow()

    fun getLeaderboard() {
        viewModelScope.launch {


            when(val response = gameRepository.getLeaderboards()){
                is ApiResult.Success -> {
                    uiState.update {
                        uiState.value.copy(
                            leaderboard = response.data
                        )
                    }
                }

                is ApiResult.Error -> {
                    globalMessageRepository.userMessageString.tryEmit(
                        "error fetching leaderboards"
                    )
                }
            }
        }
    }

}