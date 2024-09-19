package com.devid_academy.motus

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devid_academy.ui.composables.KeyboardUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


class MotusViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(MotusUiState())
    fun observeMotusUiState(): StateFlow<MotusUiState> = _uiState

    private val _keyboardUiState = MutableStateFlow(KeyboardUiState())
    fun observeKeyboardUiState(): StateFlow<KeyboardUiState> = _keyboardUiState

    private val wordList = listOf(
        "camion", "soleil", "maison", "jardin", "minute", "montre", "nuage", "couper",
        "danser", "fleurs", "gagner", "habits", "images", "jouets", "livres", "manger", "nuages", "ouvert",
        "parler", "quitter", "regard", "soeurs", "tables", "verres", "voyage", "avions", "ballon", "douche",
        "effort", "famille", "glaces", "hautes", "jamais", "koalas", "lettre", "mamans", "nature", "object",
        "plante", "quatre", "rappel", "sables", "toucan", "valise", "yachts", "zebres", "barque", "etages",
        "frites", "grille", "hommes", "insist", "justes", "karate", "lisser", "masque", "navire", "ocelot",
        "pluies", "quasar", "rapide", "tomate", "unique", "valeur", "wagons", "abbaye", "beurre", "casque",
        "dermis", "effets", "femmes", "garcon", "herbes", "musees", "noirci", "ombres", "quiche", "ramper",
        "secher", "tomber", "verrou", "xylose", "zoning", "jaloux", "kayaks"
    )

    fun setGridAndSetWord(){

        _uiState.value = MotusUiState()
        _keyboardUiState.value = KeyboardUiState()

        // draw grid
        viewModelScope.launch {
            val tempGrid = mutableListOf<MutableList<MotusLetter>>()
            for (i in 0 until _uiState.value.maxRows) {
                val row = mutableListOf<MotusLetter>()
                for (j in 0 until _uiState.value.maxMotusLetter) {
                    row.add(MotusLetter(' '))
                    _uiState.value = _uiState.value.copy(grid = tempGrid.toMutableList().apply {
                        if (size > i) set(i, row.toList().toMutableList())
                        else add(row.toList().toMutableList())
                    })
                    // delay(30)
                }
                tempGrid.add(row)
            }
        }

        // Set word to discover
        val wordToDiscover = wordList[(Math.random() * 100 % wordList.size).roundToInt()]
            .map { MotusLetter(it.uppercaseChar()) }

        _uiState.value = _uiState.value.copy(
            wordToDiscover = wordToDiscover
        )
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


    fun checkWord() {
        val grid = _uiState.value.grid
        val wordToDiscover = _uiState.value.wordToDiscover.toMutableList()

        if (_uiState.value.currentRow > _uiState.value.maxRows - 1) return
        if (_uiState.value.grid.isEmpty()) return
        if (grid[_uiState.value.currentRow].any { it.letter == ' ' }) return

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

            grid[_uiState.value.currentRow + 1] = nextRow
        }

        _uiState.value = _uiState.value.copy(
            grid = grid,
            currentRow = _uiState.value.currentRow + 1,
            currentMotusLetter = 0,
            wordToDiscover = wordToDiscover
        )
        updateKeyboardKeysColor()
    }

    private fun updateKeyboardKeysColor() {
        val grid = _uiState.value.grid
        val keyboardState = _keyboardUiState.value

        // Create a mutable copy of the keyboard letter list
        val updatedKeyboardLetterList = keyboardState.keyboardLetterList.map { it.toMutableList() }.toMutableList()

        // Iterate through the grid to update the keyboard keys color
        grid.flatten().forEach { motusLetter ->
            if (motusLetter.state == MotusLetterState.NOT_INSIDE_WORD) {
                // Find the key in the keyboardLetterList and update its color
                for (row in updatedKeyboardLetterList) {
                    row.forEachIndexed { index, key ->
                        if (key.letter == motusLetter.letter) {
                            row[index] = key.copy(
                                keyColor = Color(0xFFDB1A1A),
                                // letterColor = Color(0xFFFFFFFF),
                                isClickable = false
                            )
                        }
                    }
                }
            }
        }

        // Convert the mutable list back to an immutable list
        val immutableKeyboardLetterList = updatedKeyboardLetterList.map { it.toList() }.toMutableList()

        // Update the keyboard UI state with the new list
        _keyboardUiState.value = _keyboardUiState.value.copy(
            keyboardLetterList = immutableKeyboardLetterList
        )
    }


    fun onResetRow() {
        if (_uiState.value.grid.isEmpty())
            return

        val currentGrid = _uiState.value.grid.toMutableList()
        currentGrid[_uiState.value.currentRow] = MutableList(_uiState.value.maxMotusLetter) { MotusLetter(' ') }

        _uiState.value = _uiState.value.copy(
            grid = currentGrid,
            currentMotusLetter = 0
        )
    }

}