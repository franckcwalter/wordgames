package com.devid_academy.common.game_base

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devid_academy.common.R
import com.devid_academy.common.common.SquareIconButton
import com.devid_academy.common.utils.getRandomColor

@Composable
fun GameBase(
    onClue: () -> Unit,
    onQuitGame: () -> Unit,
    content: @Composable () -> Unit,
){
    val isDisplayingSettings = remember { mutableStateOf(false) }
    val isDisplayingQuitGame = remember { mutableStateOf(false) }

    BackHandler(onBack = { isDisplayingQuitGame.value = true})

    Box(
        Modifier
            .fillMaxWidth()
            .background(getRandomColor())
    ){
        Image(
            painter = painterResource(id = com.devid_academy.core.ui.R.drawable.app_bg),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .alpha(0.2f),
            contentScale = ContentScale.FillWidth
        )
    }

    Column(Modifier.fillMaxSize()) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 48.dp)
                .padding(bottom = 24.dp)
                .padding(horizontal = 12.dp)
            ,
            verticalAlignment = Alignment.CenterVertically,

        ){

            Image(
                modifier = Modifier
                    .width(120.dp)
                    .clickable {
                        isDisplayingQuitGame.value = true
                    },
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null
            )
            Spacer(Modifier.weight(1f))
            Text(
                "12432 points",
                )
            Spacer(Modifier.weight(1f))

            SquareIconButton(
                modifier = Modifier.padding(end = 12.dp),
                imageVector = Icons.Rounded.Settings) {
                isDisplayingSettings.value = true
            }

            SquareIconButton(
                modifier = Modifier,
                imageResId = R.drawable.mystery){
                onClue()
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            content()
        }
    }

    if (isDisplayingSettings.value){
        SettingsModal(
            onClose = { isDisplayingSettings.value = false },
            onQuitGame = { onQuitGame() }
        )
    }
    if (isDisplayingQuitGame.value){
        QuitGameModale(
            onStay = { isDisplayingQuitGame.value = false },
            onQuit = { onQuitGame() },
            stayButtonLabel = "RETOUR AU JEU",
            quitButtonLabel = "Quitter le jeu",
            text = "Voulez-vous vraiment\nquitter le jeu? "
        )
    }

}


@Preview
@Composable
private fun GameBasePreview() {
    GameBase(
        onClue = { },
        onQuitGame = { },
        content = {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color.White)){
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "GAME GAME i guess"
                )
            }
        }
    )
}