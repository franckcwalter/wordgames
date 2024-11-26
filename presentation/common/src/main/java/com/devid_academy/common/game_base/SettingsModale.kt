package com.devid_academy.common.game_base

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devid_academy.common.common.MediumButton
import com.devid_academy.common.utils.getRandomColor
import com.devid_academy.common.utils.mainPurple
import com.devid_academy.common.utils.mainRed
import com.devid_academy.common.utils.mainTurquoise
import com.devid_academy.common.utils.mainYellow

@Composable
fun SettingsModal(
    uiState: GameBaseUiState,
    updateLevelSliderPosition: (Float) -> Unit,
    updateModeSliderPosition: (Float) -> Unit,
    onStartTutorial: () -> Unit,
    onCloseAndCommitChanges: () -> Unit,
    onQuitGame: () -> Unit,
    onClose: () -> Unit
) {
    Box(
        Modifier
            .fillMaxSize()
            .background(Color.Black.copy(.2f))
            .clickable {
                onClose()
            }
    ){
        Column(
            Modifier
                .clickable(enabled=false){}
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(25.dp)
                )
                .border(
                    width = 2.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(25.dp)
                )
                .padding(vertical = 24.dp)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = "PARAMÈTRES",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 6.dp)
            )

            SettingsElement(mainYellow) {
                Column {
                    Row(Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center) {
                        Text(
                            text = "LEVEL : ",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(uiState.levelText)
                    }
                    Slider(
                        value = uiState.levelSliderPosition,
                        onValueChange = { updateLevelSliderPosition(it) },
                        colors = SliderDefaults.colors(
                            thumbColor = mainPurple,
                            activeTrackColor = mainRed,
                            inactiveTrackColor = Color.White,
                        ),
                        steps = 2,
                        valueRange = 1f..4f,
                        modifier = Modifier.padding(horizontal = 15.dp)
                    )
                    Row(Modifier.fillMaxWidth().padding(horizontal = 10.dp), Arrangement.Absolute.SpaceBetween){
                        Text("facile")
                        Text("normal")
                        Text("difficile")
                        Text("extrême")
                    }
                }
            }

            SettingsElement(mainTurquoise){
                Column (
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(Modifier.fillMaxWidth(.5f),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "MODE : ",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(uiState.modeText)
                    }
                    // TODO : mettre des radio buttons ;
                    Slider(
                        value = uiState.modeSliderPosition,
                        onValueChange = { updateModeSliderPosition(it) },
                        colors = SliderDefaults.colors(
                            thumbColor = mainPurple,
                            activeTrackColor = mainYellow,
                            inactiveTrackColor = Color.White,
                        ),
                        steps = 1,
                        valueRange = 1f..3f,
                        modifier = Modifier
                            .fillMaxWidth(.75f)
                    )
                    Row(Modifier.fillMaxWidth(.75f)
                        .padding(horizontal = 10.dp),
                        Arrangement.Absolute.SpaceBetween
                    ){
                        Text("normal")
                        Text("chrono")
                        Text("mystery")
                    }
                }
            }

            MediumButton(
                label = "Revoir le tutoriel",
                containerColor = mainPurple) {
                onStartTutorial()
            }

            Spacer(Modifier.height(24.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ){
               // Spacer(Modifier.weight(1f))
                MediumButton(
                    label = "Quitter le jeu ",
                    containerColor = mainRed) {
                    onQuitGame()
                }
              //  Spacer(Modifier.weight(1f))
                MediumButton(
                    label = "Retour au jeu",
                    containerColor = mainYellow) {
                    onCloseAndCommitChanges()
                }
            }
        }
    }
}

@Preview
@Composable
private fun SettingsModalPreview() {
    Box(
        Modifier
            .fillMaxSize()
            .background(Color.White)){
        SettingsModal(
            uiState = GameBaseUiState(),
            onClose = {},
            onQuitGame = {},
            onStartTutorial = {},
            updateLevelSliderPosition = { _ ->  },
            updateModeSliderPosition = { _ ->  },
            onCloseAndCommitChanges = { }
        )
    }
}

@Composable
fun SettingsElement(
    backgroundColor: Color,
    content: @Composable () -> Unit
) {
    Row (
        Modifier
            .padding(vertical = 6.dp)
            .padding(horizontal = 24.dp)
            .fillMaxWidth()
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(15.dp)
            )
            .border(
                width = 2.dp,
                color = Color.Black,
                shape = RoundedCornerShape(15.dp)
            )
            .padding(vertical = 24.dp)
            .clickable { },
        verticalAlignment = Alignment.CenterVertically
    ){
        content()
    }

}