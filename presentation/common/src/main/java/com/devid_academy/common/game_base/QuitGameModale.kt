package com.devid_academy.common.game_base

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devid_academy.common.common.LargeButton
import com.devid_academy.common.common.MediumButton
import com.devid_academy.core.ui.R

@Composable
fun QuitGameModale(
    onQuit: () -> Unit,
    onStay: () -> Unit,
    stayButtonLabel: String,
    quitButtonLabel: String,
    text: String,
) {

    Box(
        Modifier
            .fillMaxSize()
            .background(Color.Black.copy(.4f))
            .clickable {
                onStay()
            }
    ){
        Column(
            Modifier
                .padding(horizontal = 24.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(25.dp)
                )
                .border(
                    width = 2.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(25.dp)
                )
                .clickable(enabled = false, onClick = {})
                .padding(horizontal = 24.dp)
                .padding(vertical = 24.dp)
                .align(Alignment.Center)
                ,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = "ATTENTION",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 6.dp)
            )

            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(vertical = 12.dp),
                textAlign = TextAlign.Center,
            )

            Spacer(Modifier.height(24.dp))

            Column(horizontalAlignment = Alignment.CenterHorizontally){
                MediumButton(
                    label = quitButtonLabel,
                    containerColor = Color(0xFFfe6465)) {
                    onQuit()
                }
                Spacer(Modifier.height(8.dp))
                LargeButton(
                    label = stayButtonLabel,
                    containerColor = Color(0xFFFDB420)) {
                    onStay()
                }

            }
        }
    }
}

@Preview
@Composable
private fun QuitGameModalePreview() {
    QuitGameModale(
        onQuit = {},
        onStay = {},
        stayButtonLabel = "",
        quitButtonLabel = "",
        text = ""
    )
}