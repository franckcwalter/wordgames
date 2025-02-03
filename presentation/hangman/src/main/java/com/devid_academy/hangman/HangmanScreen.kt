package com.devid_academy.hangman

import androidx.compose.foundation.background
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.devid_academy.common.game_base.GameBase
import com.devid_academy.common.utils.getRandomColor
import com.devid_academy.ui.composables.Keyboard
import com.devid_academy.ui.composables.KeyboardUiState
import com.devid_academy.core.ui.R
import com.devid_academy.ui.composables.AllCapsButton
import org.koin.androidx.compose.getViewModel


@Composable
fun HangmanScreen(
    navController: NavHostController,
    innerPadding: PaddingValues = PaddingValues()
) {

    val viewModel = getViewModel<HangmanViewModel>()

    val uiState by viewModel.observeHangmanUiState().collectAsState()
    val keyboardUiState by viewModel.observeKeyboardUiState().collectAsState()

    GameBase(
        onQuitGame = { navController.popBackStack() },
        onClue = {  }
    ){ onRoundFinished ->

        HangmanContent(
            innerPadding = innerPadding,
            uiState = uiState,
            keyboardUiState = keyboardUiState,
            onLetterClick = {
                if (viewModel.onLetterClicked(it)) {
                    uiState.currentRound?.let { currentRound ->
                        onRoundFinished(currentRound.id, uiState.pointsToWin)
                    }
                }
            },
            setNewWord = {
                viewModel.setWord()
            }
        )
    }
}

@Composable
fun HangmanContent(
    innerPadding: PaddingValues = PaddingValues(),
    uiState: HangmanUiState,
    keyboardUiState: KeyboardUiState,
    onLetterClick: (Char) -> Unit,
    setNewWord: () -> Unit
){
    Column(
        modifier = Modifier.fillMaxSize()
            .background(getRandomColor()),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = uiState.pointsToWin.toString(),
            fontFamily = FontFamily(Font(R.font.kanit_regular)),
            fontSize = 35.sp,
            textAlign = TextAlign.End,
            color = Color(0xFFFFFFFF),
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 50.dp, bottom = 50.dp)
        )

        Text("${ uiState.wordToDiscover.map { it.letter } }")


        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()

        ){

            if (uiState.wordToDiscover.isEmpty()){
                Text(
                    text = "",
                    modifier = Modifier.padding(5.dp),
                    fontSize = 50.sp,
                    fontFamily = FontFamily(Font(R.font.kanit_regular)),
                    color = Color(0xFFFFFFFF)
                )

            }
            
            uiState.wordToDiscover.forEach {
                if (it.isDiscovered)
                    Text(
                        text = it.letter.toString(),
                        modifier = Modifier.padding(5.dp),
                        fontSize = 50.sp,
                        fontFamily = FontFamily(Font(R.font.kanit_regular)),
                        color = Color(0xFFFFFFFF)
                        )
                else
                    Text(
                        text = "_",
                        modifier = Modifier.padding(5.dp),
                        fontSize = 50.sp,
                        fontFamily = FontFamily(Font(R.font.rampartone_regular)),
                        color = Color(0xFFFFFFFF)
                    )
            }
        }

        Spacer(Modifier.height(50.dp))

        Keyboard(
            onLetterClick = {
                onLetterClick(it)
            },
            keyboardUiState
        )

        Column(modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            if (uiState.wordToDiscover.isEmpty() || uiState.userHasWon){
                AllCapsButton(
                    label = if (!uiState.userHasWon)  "COMMENCER" else "NOUVELLE PARTIE",
                    onClick = { setNewWord() },
                )
            }
        }
    }
}

data class HangmanLetter(
    val letter: Char,
    val isDiscovered: Boolean
)

@Preview
@Composable
fun HangmanContentPreview() {
    GameBase(
        onQuitGame = { },
        onClue = {  }
    ){
        HangmanContent(
            uiState = HangmanUiState(),
            keyboardUiState = KeyboardUiState(),
            onLetterClick = {  },
            setNewWord = {  }
        )
    }
}
