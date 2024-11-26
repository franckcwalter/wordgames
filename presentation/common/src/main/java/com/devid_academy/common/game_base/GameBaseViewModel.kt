package com.devid_academy.common.game_base

import androidx.lifecycle.ViewModel
import com.devid_academy.gamedata.LevelEnum
import com.devid_academy.gamedata.ModeEnum
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class GameBaseViewModel(

): ViewModel() {

    private val uiState = MutableStateFlow(GameBaseUiState())
    fun observeGameBaseUiState(): StateFlow<GameBaseUiState> = uiState

    fun init() {
        // TODO : voir avec quoi initialiser ?
        // idéalement : garder en mémoire la difficulté et le mode choisi précédemment
        uiState.update {
            it.copy(
                levelText = when(uiState.value.level){
                    LevelEnum.EASY -> "FACILE"
                    LevelEnum.MEDIUM -> "NORMAL"
                    LevelEnum.HARD -> "DIFFICILE"
                    LevelEnum.EXTREME -> "EXTRÊME"
                    else -> { "" }
                },
                modeText = when(uiState.value.mode){
                    ModeEnum.NORMAL -> "Normal"
                    ModeEnum.CHRONO -> "Chrono"
                    ModeEnum.MYSTERY -> "Mystery"
                }
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
                    4f -> LevelEnum.EXTREME
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

    fun updateLevelSlider(sliderPosition: Float) {
        uiState.update {
            it.copy(
                levelSliderPosition = sliderPosition
            )
        }
        uiState.update {
            it.copy(
                levelText = when (sliderPosition) {
                    1f -> "facile"
                    2f -> "normal"
                    3f -> "difficile"
                    4f -> "extrême"
                    else -> { "" }
                }
            )
        }
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
        //   TODO("Not yet implemented")
    }


}