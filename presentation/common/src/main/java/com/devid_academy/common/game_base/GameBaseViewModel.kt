package com.devid_academy.common.game_base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devid_academy.auth.UserRepository
import com.devid_academy.gamedata.GameRepository
import com.devid_academy.ui.LevelEnum
import com.devid_academy.ui.ModeEnum
import com.devid_academy.ui.SharedPrefsManager
import com.devid_academy.ui.SharedPrefsManager.USER_ID
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GameBaseViewModel(
    private val gameRepository: GameRepository,
    private val userRepository: UserRepository
): ViewModel() {

    private val uiState = MutableStateFlow(GameBaseUiState())
    fun observeGameBaseUiState(): StateFlow<GameBaseUiState> = uiState

    fun init(gameName: String) {

        uiState.update {
            it.copy(
                levelText = uiState.value.level.displayName,

                modeText = when(uiState.value.mode){
                    ModeEnum.NORMAL -> "Normal"
                    ModeEnum.CHRONO -> "Chrono"
                    ModeEnum.MYSTERY -> "Mystery"
                }
            )
        }
        getTotalPoints()
        getLevelForGame(gameName)
    }

    private fun getTotalPoints(){
        viewModelScope.launch {
            Log.e("getTotalPoints","${gameRepository.getTotalPoints()}")
            uiState.update {
                uiState.value.copy(
                    totalPoints = gameRepository.getTotalPoints()
                )
            }
        }
    }

    private fun getLevelForGame(gameName: String) {
        viewModelScope.launch {

            val level = gameRepository.getLevelByGameName(gameName)
            uiState.update {
                uiState.value.copy(
                    level = level,
                    levelSliderPosition = when(level){
                        LevelEnum.EASY -> 1f
                        LevelEnum.MEDIUM -> 2f
                        LevelEnum.HARD -> 3f
                    }
                )
            }
        }
    }

    fun onRoundFinished(roundId: Long, points: Long) {

        Log.e("GameBaseViewModel  onRoundFinished() ", "roundId: $roundId + points: $points")
        
        viewModelScope.launch {
            gameRepository.insertFinishedRound(roundId, points, SharedPrefsManager[USER_ID])
        }

        uiState.update {
            uiState.value.copy(
                totalPoints = uiState.value.totalPoints + points
            )
        }
    }

    fun toggleIsDisplayingQuitGame() {
        uiState.update {
            it.copy(
                isDisplayingQuitGame = !it.isDisplayingQuitGame
            )
        }
    }

    fun closeModalAndCommitChanges() {
        uiState.update {
            it.copy(
                level = when(uiState.value.levelSliderPosition) {
                    1f -> LevelEnum.EASY
                    2f -> LevelEnum.MEDIUM
                    3f -> LevelEnum.HARD
                    else -> { LevelEnum.EASY }
                },
                mode = when(uiState.value.modeSliderPosition){
                    1f -> ModeEnum.NORMAL
                    2f -> ModeEnum.CHRONO
                    3f -> ModeEnum.MYSTERY
                    else -> ModeEnum.NORMAL
                }
            )
        }
        toggleIsDisplayingSettings()
    }

    fun toggleIsDisplayingSettings() {
        uiState.update {
            it.copy(
                isDisplayingSettings = !it.isDisplayingSettings
            )
        }
    }
    fun updateLevelSlider(sliderPosition: Float): LevelEnum {
        val level = when (sliderPosition) {
            1f -> LevelEnum.EASY
            2f -> LevelEnum.MEDIUM
            3f -> LevelEnum.HARD
            else -> LevelEnum.EASY
        }

        uiState.update {
            it.copy(
                levelSliderPosition = sliderPosition,
                levelText = level.displayName
            )
        }

        return level
    }


    fun updateModeSlider(sliderPosition: Float) {
        uiState.update {
            it.copy(
                modeSliderPosition = sliderPosition
            )
        }
        uiState.update {
            it.copy(
                modeText = when (sliderPosition) {
                    1f -> "normal"
                    2f -> "chrono"
                    3f -> "mystère"
                    else -> { "" }
                }
            )
        }
    }

    fun startTutorial() {
        // TODO("Not yet implemented")
    }

    fun postRoundsFinished(){
        userRepository.getAccessToken()?.let {
            viewModelScope.launch {
                gameRepository.postRoundsFinished()
            }
        }
    }

}