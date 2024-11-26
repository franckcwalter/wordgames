package com.devid_academy.common.game_base

import com.devid_academy.gamedata.LevelEnum
import com.devid_academy.gamedata.ModeEnum

data class GameBaseUiState(

    val isDisplayingSettings: Boolean = false,
    val isDisplayingQuitGame: Boolean = false,

    val levelSliderPosition: Float = 0f,
    val modeSliderPosition: Float = 0f,

    val level: LevelEnum = LevelEnum.EASY,
    val mode: ModeEnum = ModeEnum.NORMAL,

    val levelText: String = "",
    val modeText: String = ""

)
