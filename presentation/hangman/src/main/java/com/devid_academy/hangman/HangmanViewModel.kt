package com.devid_academy.hangman

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devid_academy.gamedata.GameRepository
import com.devid_academy.gamedata.LevelEnum
import com.devid_academy.ui.composables.KeyboardUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class HangmanViewModel(
    private val gameRepository: GameRepository
) : ViewModel(){

    private val _uiState = MutableStateFlow(HangmanUiState())
    fun observeHangmanUiState(): StateFlow<HangmanUiState> = _uiState

    private val _keyboardUiState = MutableStateFlow(KeyboardUiState())
    fun observeKeyboardUiState(): StateFlow<KeyboardUiState> = _keyboardUiState


    init {
        getGameData()
    }

    private fun getGameData(){
        viewModelScope.launch {
            val response = gameRepository.getRoundsByGameAndLevel("hangman", LevelEnum.EASY.toString())
            Log.d("HangmanViewModel getGameData", "Fetched response: $response")
            _uiState.update { currentState ->
                currentState.copy(roundList = response)
            }
            Log.d("HangmanViewModel getGameData", "Updated state: ${_uiState.value}")
        }
    }

    fun setWord() {
        if (_uiState.value.roundList.isEmpty()) { return }
    
        val randomIndex = (0 until _uiState.value.roundList.size).random()
        val selectedRound = _uiState.value.roundList[randomIndex]
    
        _uiState.update { 
            HangmanUiState(
                roundList = it.roundList,
                currentRound = selectedRound
            )
        }
        _keyboardUiState.update {
            KeyboardUiState()
        }
    
        viewModelScope.launch {
            selectedRound.data.first().forEach { value ->
                delay(100)
                _uiState.update { currentState ->
                    currentState.copy(
                        wordToDiscover = currentState.wordToDiscover.toMutableList().also {
                            it.add(HangmanLetter(value, false))
                        }
                    )
                }
            }
        }
    }

    private fun checkWinCondition() {
        val hasWon = _uiState.value.wordToDiscover.all { it.isDiscovered }
        if (hasWon) {
            _uiState.update { 
                it.copy(userHasWon = true)
            }
        }
    }

    private fun checkLoseCondition() {
        if (_uiState.value.pointsToWin == 0L) {
            _uiState.update { 
                it.copy(userHasLost = true)
            }
            // TODO: inform user they have lost then set new word
            setWord()
        }
    }

    fun onLetterClicked(letterClicked: Char): Boolean {
        if(_uiState.value.pointsToWin > 0) {
            val wordToDiscover = _uiState.value.wordToDiscover.toMutableList()
            var isRightGuess = false
    
            wordToDiscover.forEachIndexed { index, hangmanLetter ->
                if (hangmanLetter.letter.lowercaseChar() == letterClicked.lowercaseChar()) {
                    isRightGuess = true
                    wordToDiscover[index] = hangmanLetter.copy(isDiscovered = true)
                }
            }
    
            val keyboardLetterList = _keyboardUiState.value.keyboardLetterList.map { row ->
                row.map { key ->
                    if (key.letter.lowercaseChar() == letterClicked.lowercaseChar()) {
                        key.copy(
                            hasBeenPressed = true, 
                            keyColor = if (isRightGuess) Color(0xFF54DB1A) else Color(0xFFDB1A1A)
                        )
                    } else {
                        key
                    }
                }
            }.toMutableList()
    
            _keyboardUiState.update { 
                it.copy(keyboardLetterList = keyboardLetterList)
            }
    
            _uiState.update { 
                if(isRightGuess) {
                    it.copy(wordToDiscover = wordToDiscover)
                } else {
                    it.copy(pointsToWin = it.pointsToWin - 1)
                }
            }

            checkWinCondition()
            checkLoseCondition()
            return _uiState.value.userHasWon
        }
        return false
    }

}

