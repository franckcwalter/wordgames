package com.devid_academy.ui.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devid_academy.core.ui.R

@Composable
fun Keyboard(
    onLetterClick: (Char) -> Unit,
    uiState : KeyboardUiState
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
                            onClick = { if (key.isClickable) onLetterClick(key.letter) },
                            contentPadding = PaddingValues(0.dp),
                            shape = RoundedCornerShape(6.dp),
                            elevation = ButtonDefaults.elevatedButtonElevation(
                                defaultElevation = 8.dp,
                                pressedElevation = 2.dp
                            ),
                            border = BorderStroke(2.dp, Color(Color.Black.toArgb())),
                            modifier = Modifier
                                .padding(horizontal = 4.dp, vertical = 6.dp)
                                .width(28.dp)
                                .height(36.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = uiState.keyboardLetterList[rowIndex][index].keyColor,
                                contentColor = uiState.keyboardLetterList[rowIndex][index].letterColor
                            )
                        ){
                            Text(
                                text = key.letter.toString(),
                                fontFamily = FontFamily(Font(R.font.kanit_regular)),
                                fontSize = 24.sp,
                                modifier = Modifier
                                    .offset(y = 0.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

data class KeyboardUiState(
    val keyboardLetterList: MutableList<List<Key>> = mutableListOf(
        listOf(Key('A'), Key('Z'), Key('E'), Key('R'), Key('T'), Key('Y'), Key('U'), Key('I'), Key('O'), Key('P')),
        listOf(Key('Q'), Key('S'), Key('D'), Key('F'), Key('G'), Key('H'), Key('J'), Key('K'), Key('L'), Key('M')),
        listOf(Key('W'), Key('X'), Key('C'), Key('V'), Key('B'), Key('N'))
    )
)

data class Key(
    val letter: Char,
    val letterColor: Color = Color(0xFF000000),
    val keyColor: Color = Color(0xFFFFFFFF),
    val hasBeenPressed: Boolean = false,
    val isClickable: Boolean = true
)
