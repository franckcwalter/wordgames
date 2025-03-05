package com.devid_academy.motus

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.devid_academy.common.game_base.GameBase
import com.devid_academy.common.utils.getRandomColor
import com.devid_academy.core.ui.R
import com.devid_academy.ui.composables.AllCapsButton
import com.devid_academy.ui.composables.Keyboard
import com.devid_academy.ui.composables.KeyboardUiState
import org.koin.androidx.compose.getViewModel


@Composable
fun MotusScreen(
    navController: NavHostController,
    innerPadding: PaddingValues = PaddingValues()
) {

    val viewModel = getViewModel<MotusViewModel>()
    val uiState by viewModel.observeMotusUiState().collectAsState()
    val keyboardUiState by viewModel.observeKeyboardUiState().collectAsState()


    LaunchedEffect(navController.currentBackStackEntry) {
        navController.currentBackStackEntry?.destination?.route?.let { Log.e("motus", it) }

        if (navController.currentBackStackEntry?.destination?.route == "motus"){
            viewModel.getGameData()
            viewModel.startGameSession("motus")
        }
    }

    GameBase(
        gameName = "motus",
        onClue = {},
        onQuitGame = {
            viewModel.endGameSession()
            viewModel.resetUiState()
            navController.popBackStack()
        },
        onUpdateLevel = { level ->
            viewModel.getGameData(level)
            viewModel.resetUiState()
        }
    ) { onRoundFinished ->

        MotusContent(
            innerPadding = innerPadding,
            uiState = uiState,
            keyboardUiState = keyboardUiState,
            startGame = {
                viewModel.setGridAndSetWord()
            },
            onLetterClick = {
                viewModel.addLetterToGrid(it)
            },
            onCheckClick = {
                if (viewModel.checkWord()){
                    uiState.currentRound?.let {
                        onRoundFinished(it.id, uiState.pointsToWin)
                    }
                }
            },
            onResetRow = {
                viewModel.onResetRow()
            }
        )
    }
}


@Composable
fun MotusContent(
    innerPadding: PaddingValues = PaddingValues(),
    uiState: MotusUiState,
    keyboardUiState: KeyboardUiState,
    startGame: () -> Unit,
    onLetterClick: (Char) -> Unit,
    onCheckClick: () -> Unit,
    onResetRow: () -> Unit
){


    Column(
        Modifier
            .fillMaxSize()
            .background(getRandomColor()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        /*
        Row {
            uiState.wordToDiscover.forEach { value ->
                Text(text = value.letter.toString())
            }
        }*/

        /*
        Row {
            uiState.wordToDiscover.forEach { value ->
                Text(text = value.state.toString())
            }
        }*/

        Row {
            Spacer(Modifier.weight(1f))
            Text("Points à gagner : ${uiState.pointsToWin} points")
        }
        Row {
            Spacer(Modifier.weight(1f))
            Text("level: ${uiState.level}")
        }

        Row {
            Text("${ uiState.wordToDiscover.map { it.letter } }")
        }

        MotusGrid(uiState)

        Spacer(Modifier.height(50.dp))

        Keyboard(
            onLetterClick = {
                onLetterClick(it)
            },
            uiState = keyboardUiState
        )

        Spacer(Modifier.height(50.dp))

        Row {
            if (uiState.wordToDiscover.isEmpty() || uiState.userHasWon){
                AllCapsButton(
                    label = if (!uiState.userHasWon)  "COMMENCER" else "NOUVELLE PARTIE",
                    onClick = { startGame() }
                )
            } else {
                Spacer(Modifier.width(8.dp))
                AllCapsButton(
                    label = "reset row",
                    onClick = { onResetRow() }
                )
                Spacer(Modifier.width(8.dp))
                AllCapsButton(
                    label = "VÉRIFIER",
                    onClick = { onCheckClick() }
                )
            }
        }
    }
}


@Preview
@Composable
private fun MotusContentPreview() {
    GameBase(
        gameName = "motus",
        onClue = {},
        onQuitGame = {},
        onUpdateLevel = {_->}
    ) {
        MotusContent(
            uiState = MotusUiState().copy(
                grid = mutableListOf(
                    mutableListOf(
                        MotusLetter('A'),
                        MotusLetter('B'),
                        MotusLetter('C'),
                        MotusLetter('A'),
                        MotusLetter('B'),
                        MotusLetter('C')
                    ),
                    mutableListOf(
                        MotusLetter('A'),
                        MotusLetter('B'),
                        MotusLetter('C'),
                        MotusLetter('A'),
                        MotusLetter('B'),
                        MotusLetter('C')
                    ),
                    mutableListOf(
                        MotusLetter('A'),
                        MotusLetter('B'),
                        MotusLetter('C'),
                        MotusLetter('A'),
                        MotusLetter('B'),
                        MotusLetter('C')
                    ),
                )
            ),
            keyboardUiState = KeyboardUiState(),
            startGame = { },
            onLetterClick = { },
            onCheckClick = { },
            onResetRow = { }
        )
    }
}


@Composable
fun MotusGrid(
    motusUiState: MotusUiState
){

    Column {

        motusUiState.grid.forEach { value ->

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ){

                value.forEach { motusLetter ->
                    Box(
                        Modifier
                            .padding(vertical = 2.dp, horizontal = 2.dp)
                            .size(50.dp)
                            .border(
                                BorderStroke(2.dp, Color.Black),
                                shape = RoundedCornerShape(15.dp)
                            )
                            .background(
                                color = when (motusLetter.state) {
                                    MotusLetterState.INITIAL -> Color(0xFFFFFFFF)
                                    MotusLetterState.INSIDE_WORD -> Color(0xFFDBA41A)
                                    MotusLetterState.CORRECT -> Color(0xFF54DB1A) // FF54DB1A
                                    MotusLetterState.NOT_INSIDE_WORD -> Color(0xFFDB1A1A)
                                },
                                shape = RoundedCornerShape(15.dp)
                            )
                    ){

                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = motusLetter.letter.toString(),
                            fontFamily = FontFamily(Font(R.font.kanit_regular)),
                            fontSize = 24.sp
                        )
                    }
                }
            }
        }
    }

}



data class MotusLetter(
    val letter: Char,
    val state: MotusLetterState = MotusLetterState.INITIAL
)

enum class MotusLetterState {
    INITIAL, NOT_INSIDE_WORD, INSIDE_WORD, CORRECT
}