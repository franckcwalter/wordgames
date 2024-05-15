package com.devid_academy.hangman

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

@Composable
fun HangmanScreen(
    navController: NavHostController,
    innerPadding: PaddingValues = PaddingValues()
) {

    val viewModel : HangmanViewModel = viewModel()
    val uiState by viewModel.observeMapUIState().collectAsState()

    HangmanContent(
        innerPadding = innerPadding,
        uiState = uiState,
        onLetterClick = {
            viewModel.onLetterClicked(it)
        },
        setNewWord = {
            viewModel.setWord()
        }
    )
}

@Composable
fun HangmanContent(
    innerPadding: PaddingValues = PaddingValues(),
    uiState: HangmanUiState,
    onLetterClick: (Char) -> Unit,
    setNewWord: () -> Unit
){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = uiState.counter.toString(),
            fontSize = 35.sp,
            textAlign = TextAlign.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 50.dp, bottom = 50.dp)
        )

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()

        ){
            uiState.wordToDiscover.forEach {

                if (it.isDiscovered)
                    Text(
                        text = it.letter.toString(),
                        modifier = Modifier.padding(5.dp),
                        fontSize = 50.sp
                    )
                else
                    Text(
                        text = "_",
                        modifier = Modifier.padding(5.dp),
                        fontSize = 50.sp
                    )
            }
        }

        Keyboard(
            onLetterClick = {
                onLetterClick(it)
            },
            uiState
        )

        Box(modifier = Modifier.fillMaxWidth()
            .height(200.dp)
        ){
            Button(
                onClick = { setNewWord() },
                modifier = Modifier.align(Alignment.Center)
            ) {
                Text("Start!")
            }
        }
    }
}

@Composable
fun Keyboard(
    onLetterClick: (Char) -> Unit,
    uiState : HangmanUiState
) {

    Box(modifier = Modifier
        .fillMaxWidth()
    ){


        Column{
            uiState.keyboardLetterList.forEachIndexed { rowIndex,  letterList ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ){
                    letterList.forEachIndexed { index, key ->
                        Button(
                            onClick = { onLetterClick(key.letter) },
                            contentPadding = PaddingValues(0.dp),
                            modifier = Modifier
                                .padding(horizontal = 5.dp, vertical = 5.dp)
                                .width(25.dp)
                                .height(30.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = uiState.keyboardLetterList[rowIndex][index].keyColor,
                                contentColor = uiState.keyboardLetterList[rowIndex][index].letterColor
                            )
                        ){
                            Text(
                                text = key.letter.toString(),
                            )
                        }
                    }
                }
            }
        }
    }
}

data class Key(
    val letter: Char,
    val letterColor: Color = Color(0xFFFFFFFF),
    val keyColor: Color = Color(0xFF000000),
    val hasBeenPressed: Boolean = false
)

data class HangmanLetter(
    val letter: Char,
    val isDiscovered: Boolean
)

@Preview
@Composable
fun HangmanContentPreview() {
    HangmanContent(
        uiState = HangmanUiState(),
        onLetterClick = {  },
        setNewWord = {  }
    )
}

