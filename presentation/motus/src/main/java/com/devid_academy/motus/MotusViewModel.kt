package com.devid_academy.motus

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devid_academy.distant.ApiResult
import com.devid_academy.gamedata.GameRepository
import com.devid_academy.ui.GlobalMessageRepository
import com.devid_academy.ui.LevelEnum
import com.devid_academy.ui.composables.KeyboardUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class MotusViewModel (
    private val gameRepository: GameRepository,
    private val globalMessageRepository: GlobalMessageRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(MotusUiState())
    fun observeMotusUiState(): StateFlow<MotusUiState> = _uiState
    // val uiState = observeMotusUiState()

    private val _keyboardUiState = MutableStateFlow(KeyboardUiState())
    fun observeKeyboardUiState(): StateFlow<KeyboardUiState> = _keyboardUiState

    fun getGameData(level: LevelEnum? = null){
        viewModelScope.launch {
            val response  = gameRepository.getGameDataByGameAndLevel("motus", level)
            Log.d("MotusViewModel getGameData", "Fetched response: $response")

            gameRepository.addData("userChoseDifficulty", level ?: "EASY")

            _uiState.update { currentState ->
                currentState.copy(
                    wordList = response.rounds,
                    level = response.level
                )
            }
            Log.d("MotusViewModel getGameData", "Updated state: ${_uiState.value}")
        }
    }

    fun setGridAndSetWord() {
        Log.e("MotusViewModel", "setGridAndSetWord()")

        gameRepository.incrementMetric("roundPlayed")

        // Préparer le mot à découvrir et le round
        var newWordToDiscover = listOf<MotusLetter>()
        var newCurrentRound = _uiState.value.currentRound
    
        if (_uiState.value.wordList.isNotEmpty()) {
            val round = _uiState.value.wordList[(Math.random() * _uiState.value.wordList.size).toInt()]
            newWordToDiscover = round.data[0].map { MotusLetter(it.uppercaseChar()) }
            newCurrentRound = round
        }
    
        // Déterminer la longueur du mot
        val wordLength = newWordToDiscover.size

        // Préparer la grille
        val tempGrid = mutableListOf<MutableList<MotusLetter>>()
        for (i in 0 until 6) {  // Nombre de lignes fixe, mais pourrait être dynamique aussi
            val row = mutableListOf<MotusLetter>()
            for (j in 0 until wordLength) {
                row.add(MotusLetter(' '))
            }
            tempGrid.add(row)
        }
    
        // Mise à jour unique de l'état
        _uiState.update {
            it.copy(
                grid = tempGrid,
                currentRow = 0,
                maxRows = 6,  // Peut être ajusté si nécessaire
                currentMotusLetter = 0,
                maxMotusLetter = wordLength,
                wordToDiscover = newWordToDiscover,
                currentRound = newCurrentRound,
                pointsToWin = 10,
                userHasWon = false
            )
        }
    
        // Reset du clavier
        _keyboardUiState.value = KeyboardUiState()
    }


    fun addLetterToGrid(letterClicked: Char) {

        if (_uiState.value.currentMotusLetter > _uiState.value.maxMotusLetter - 1)
            return
        if (_uiState.value.grid.isEmpty())
            return

        val currentGrid = _uiState.value.grid

        while (_uiState.value.currentMotusLetter <= _uiState.value.maxMotusLetter - 1 &&
            currentGrid[_uiState.value.currentRow][_uiState.value.currentMotusLetter].state == MotusLetterState.CORRECT) {
            _uiState.value = _uiState.value.copy(currentMotusLetter = _uiState.value.currentMotusLetter + 1)
        }

        if (_uiState.value.currentMotusLetter > _uiState.value.maxMotusLetter - 1)
            return

        currentGrid[_uiState.value.currentRow][_uiState.value.currentMotusLetter] = MotusLetter(letterClicked.uppercaseChar())

        _uiState.value = _uiState.value.copy(
            grid = currentGrid,
            currentMotusLetter = _uiState.value.currentMotusLetter + 1
        )
    }


    private fun checkLoseCondition() {
        if (!_uiState.value.userHasWon && 
            (_uiState.value.currentRow == _uiState.value.maxRows || _uiState.value.pointsToWin <= 0)) {
            _uiState.update {
                it.copy(userHasLost = true)

            }
            // TODO: inform user they have lost 
            setGridAndSetWord()
            gameRepository.incrementMetric("userHasLost")
        }
    }

    fun checkWord(): Boolean {

        gameRepository.incrementMetric("checkWordButtonClick")

        val grid = _uiState.value.grid
        val wordToDiscover = _uiState.value.wordToDiscover.toMutableList()

        if (_uiState.value.currentRow > _uiState.value.maxRows - 1) return false
        if (_uiState.value.grid.isEmpty()) return false
        if (grid[_uiState.value.currentRow].any { it.letter == ' ' }) return false
        if (_uiState.value.userHasWon) return false

        val updatedWordToDiscover = wordToDiscover.toMutableList()

        grid[_uiState.value.currentRow].forEachIndexed { indexGridLetter, gridLetter ->
            var foundInWord = false
            var correctPosition = false

            wordToDiscover.forEachIndexed { indexLetterToDiscover, letterToDiscover ->
                if (gridLetter.letter == letterToDiscover.letter) {
                    foundInWord = true
                    if (indexGridLetter == indexLetterToDiscover) {
                        correctPosition = true
                    }
                }
            }

            updatedWordToDiscover[indexGridLetter] = if (correctPosition) {
                gridLetter.copy(state = MotusLetterState.CORRECT)
            } else if (foundInWord) {
                gridLetter.copy(state = MotusLetterState.INSIDE_WORD)
            } else {
                gridLetter.copy(state = MotusLetterState.NOT_INSIDE_WORD)
            }
        }

        grid[_uiState.value.currentRow] = updatedWordToDiscover

        val userHasWon = _uiState.value.grid[_uiState.value.currentRow].all { it.state == MotusLetterState.CORRECT }

        if (_uiState.value.currentRow < _uiState.value.maxRows - 1) {
            val nextRow = grid[_uiState.value.currentRow + 1].toMutableList()

            // Ensure that letters marked as CORRECT in previous rows remain CORRECT
            for (row in 0.._uiState.value.currentRow) {
                grid[row].forEachIndexed { index, motusLetter ->
                    if (motusLetter.state == MotusLetterState.CORRECT) {
                        nextRow[index] = motusLetter
                    }
                }
            }
            if (!userHasWon)
                grid[_uiState.value.currentRow + 1] = nextRow
        }

        _uiState.value = _uiState.value.copy(
            grid = grid,
            currentRow = if (!userHasWon) _uiState.value.currentRow + 1 else _uiState.value.currentRow,
            currentMotusLetter = 0,
            wordToDiscover = wordToDiscover
        )

        updateKeyboardKeysColor()

        if(!userHasWon)
            updatePointsToWin()
        else {
            gameRepository.incrementMetric("userHasWon")
            gameRepository.addData("amoutOfPointsUserWon", _uiState.value.pointsToWin)
        }

        _uiState.update {
            _uiState.value.copy(
                userHasWon = userHasWon
            )
        }

        checkLoseCondition()


        return userHasWon
    }

    private fun updatePointsToWin(){
        _uiState.update {
            _uiState.value.copy(
                pointsToWin = _uiState.value.pointsToWin - 1
            )
        }
    }

    private fun updateKeyboardKeysColor() {
        val grid = _uiState.value.grid
        val keyboardState = _keyboardUiState.value
        val updatedKeyboardLetterList = keyboardState.keyboardLetterList.map { it.toMutableList() }.toMutableList()

        grid.flatten().forEach { motusLetter ->
            for (row in updatedKeyboardLetterList) {
                row.forEachIndexed { index, key ->
                    if (key.letter == motusLetter.letter) {
                        val keyColor = when (motusLetter.state) {
                            MotusLetterState.CORRECT -> Color(0xFF54DB1A)      // Vert
                            MotusLetterState.INSIDE_WORD -> Color(0xFFFFB74D)  // Orange
                            MotusLetterState.NOT_INSIDE_WORD -> Color(0xFFDB1A1A) // Rouge
                            else -> key.keyColor
                        }
                        row[index] = key.copy(
                            keyColor = keyColor,
                            isClickable = motusLetter.state != MotusLetterState.NOT_INSIDE_WORD
                        )
                    }
                }
            }
        }

        val immutableKeyboardLetterList = updatedKeyboardLetterList.map { it.toList() }.toMutableList()
        _keyboardUiState.update { 
            it.copy(keyboardLetterList = immutableKeyboardLetterList)
        }
    }


    fun onResetRow() {
        if (_uiState.value.grid.isEmpty()) return
        if (_uiState.value.userHasWon) return

        val currentGrid = _uiState.value.grid.toMutableList()
        currentGrid[_uiState.value.currentRow] = MutableList(_uiState.value.maxMotusLetter) { MotusLetter(' ') }

        _uiState.value = _uiState.value.copy(
            grid = currentGrid,
            currentMotusLetter = 0
        )
        gameRepository.incrementMetric("resetRowButtonClick")
    }

    fun resetUiState() {
        _uiState.update {
            MotusUiState(
                wordList = it.wordList,
                grid = mutableListOf(),
                currentRow = 0,
                maxRows = 6,
                currentMotusLetter = 0,
                maxMotusLetter = 6,
                wordToDiscover = listOf(),
                currentRound = null,
                pointsToWin = 10,
                userHasWon = false
            )
        }
        _keyboardUiState.value = KeyboardUiState()
    }

    /*** Session analytics  ***/

    fun startGameSession(gameName: String) {
        Log.e("Motus", "startGameSession()")
        gameRepository.startGameSession(gameName)
    }

    fun endGameSession() {
        Log.e("motus", "endGameSession()")
        CoroutineScope(Dispatchers.IO).launch {
            when (val response = gameRepository.postGameAnalytics()) {
                is ApiResult.Success -> {
                    globalMessageRepository.userMessageString.tryEmit(
                        "id analytics ${response.data}"
                    )
                }
                is ApiResult.Error -> {
                    globalMessageRepository.userMessageString.tryEmit(
                        "erreur insertion analytics"
                    )
                }
            }
        }
    }


    /*** End Session analytics  ***/

}

