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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devid_academy.common.common.MediumButton
import com.devid_academy.core.ui.R

@Composable
fun SettingsModal (
    onClose: () -> Unit,
    onQuitGame: () -> Unit,
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
                .align(Alignment.Center)
                .clickable { },
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = "PARAMÈTRES",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 6.dp)
            )
            Row (
                Modifier
                    .padding(vertical = 6.dp)
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth()
                    .background(
                        color = Color(0xFFFDB420),
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

            }

            Row (
                Modifier
                    .padding(vertical = 6.dp)
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth()
                    .background(
                        color = Color(0xFF4EE5D0),
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

            }

            Row (
                Modifier
                    .clickable(enabled = false, onClick = {})
                    .padding(vertical = 6.dp)
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth()
                    .background(
                        color = Color(0xFF6F89FE),
                        shape = RoundedCornerShape(15.dp)
                    )
                    .border(
                        width = 2.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(15.dp)
                    )
                    .padding(vertical = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ){

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
                    containerColor = Color(0xFFfe6465)) {
                    onQuitGame()
                }
              //  Spacer(Modifier.weight(1f))
                MediumButton(
                    label = "Retour au jeu",
                    containerColor = Color(0xFFFDB420)) {
                    onClose()
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
            onClose = {},
            onQuitGame = {}
        )
    }

}