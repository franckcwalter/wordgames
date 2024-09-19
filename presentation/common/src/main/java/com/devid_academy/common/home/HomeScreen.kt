package com.devid_academy.common.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Public
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.devid_academy.common.R
import com.devid_academy.ui.model.Route
import org.koin.androidx.compose.getViewModel


@Composable
fun HomeScreen(
    navController: NavHostController,
    innerPadding: PaddingValues
){
    val viewModel = getViewModel<HomeViewModel>()
    val homeUiState = viewModel.observeUiState().collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getGameList()
    }

    HomeContent(
        homeUiState = homeUiState.value,
        innerPadding = innerPadding,
        onOfflineOnline = {
            viewModel.toggleOfflineOnlineMode()
        },
        onGameCard = { selectedGameId ->
            viewModel.updateSelectedGame(selectedGameId)
        },
        onStartGame = {
            when (homeUiState.value.selectedGameId) {
                "motus" -> Route.MotusScreen.name
                "pendu" -> Route.HangmanScreen.name
                else -> null
            }?.let {
                navController.navigate(it)
            }
        },
        onProfile = {
            // navController.navigate(Route.Profile.name)
        },
        onStats = {
            // navController.navigate(Route.Stats.name)
        }
    )
}

@Composable
fun HomeContent(
    homeUiState : HomeUiState,
    innerPadding: PaddingValues = PaddingValues(),
    onOfflineOnline: () -> Unit,
    onGameCard: (String) -> Unit,
    onStartGame: () -> Unit,
    onProfile: () -> Unit,
    onStats: () -> Unit
){
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFfecb36))
    ){
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Spacer(Modifier.height(48.dp))
            Box (Modifier.fillMaxWidth()){
                Image(painter = painterResource(id = R.drawable.logo),
                    contentDescription = stringResource(R.string.image_content_desc_logo),
                    modifier = Modifier
                        .align(Center)
                        .height(48.dp),
                    contentScale = ContentScale.FillHeight
                )

                Button(
                    onClick = onOfflineOnline,
                    modifier = Modifier
                        .padding(end = 12.dp)
                        .align(Alignment.CenterEnd)
                        .size(48.dp)
                    ,
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.Black,
                        containerColor = Color.White,
                    ),
                    shape = RoundedCornerShape(15.dp),
                    border = BorderStroke(2.dp, Color.Black)
                ){
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = Icons.Rounded.Public,
                        contentDescription = stringResource(R.string.image_content_desc_offline_online_button)
                    )
                }
            }

            Spacer(Modifier.height(48.dp))

            Text(
                text = "Choisissez un jeu\n et commencez à jouer !",
                fontFamily = FontFamily(Font(com.devid_academy.core.ui.R.font.kanit_regular)),
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(48.dp))

            homeUiState.gameInfoList.forEach {
                 HomeContentGameCard(
                     gameInfo = it,
                     selectedGameId = homeUiState.selectedGameId,
                     onGameCard = { selectedGameId ->
                        onGameCard(selectedGameId)
                     }
                 )
            }

            Spacer(Modifier.height(40.dp))


            Button(
                onClick = onStartGame,
                modifier = Modifier,
             //   contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.Black,
                    containerColor = Color(0xFFfdb420),
                ),
                shape = RoundedCornerShape(15.dp),
                border = BorderStroke(2.dp, Color.Black)
            ) {
                Text(
                    text = "COMMENCER À JOUER !",
                    fontFamily = FontFamily(Font(com.devid_academy.core.ui.R.font.kanit_regular)),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.height(12.dp))

            Row() {
                Button(
                    onClick = onStats,
                    modifier = Modifier,
                    //   contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.Black,
                        containerColor = Color(0xFFfe6465),
                    ),
                    shape = RoundedCornerShape(15.dp),
                    border = BorderStroke(2.dp, Color.Black)
                ) {
                    Text(
                        text = "Statistiques",
                        fontFamily = FontFamily(Font(com.devid_academy.core.ui.R.font.kanit_regular)),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(Modifier.width(12.dp))
                Button(
                    onClick = onProfile,
                    modifier = Modifier,
                    //   contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.Black,
                        containerColor = Color(0xFF4de5d0),
                    ),
                    shape = RoundedCornerShape(15.dp),
                    border = BorderStroke(2.dp, Color.Black)
                ) {
                    Text(
                        text = "Profile",
                        fontFamily = FontFamily(Font(com.devid_academy.core.ui.R.font.kanit_regular)),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    )
                }
            }

        }
    }
}



@Composable
fun HomeContentGameCard(
    gameInfo: GameInfo,
    selectedGameId : String,
    onGameCard: (String) -> Unit
){

    // TODO : vérif pour le highlight du jeu sélectionné  (card rapetisse)
    Row(
        modifier = Modifier
            .padding(bottom = 8.dp)
            .padding(horizontal = if (gameInfo.id == selectedGameId) 22.dp else 24.dp)
            .clip(RoundedCornerShape(15.dp))
            .clickable { onGameCard(gameInfo.id) }
            .background(
                color = Color.White,
                shape = RoundedCornerShape(15.dp)
            )
            .height(96.dp)
            .border(
                width = 2.dp,
                color = if (gameInfo.id == selectedGameId) Color(0xFF4cdac6) else Color.Transparent,
                shape = RoundedCornerShape(15.dp)
            )
            .border(
                width = if (gameInfo.id == selectedGameId) 4.dp else 2.dp,
                color = Color.Black,
                shape = RoundedCornerShape(15.dp)
            ),
        verticalAlignment = Alignment.CenterVertically
    ){

        Image(
            painter = painterResource(id = gameInfo.imageRes),
            contentDescription = null,
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .size(60.dp),
            contentScale = ContentScale.FillBounds,
        )

        Column(){
            Text(
                text = gameInfo.name,
                fontFamily = FontFamily(Font(com.devid_academy.core.ui.R.font.kanit_regular)),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text = gameInfo.description,
                fontFamily = FontFamily(Font(com.devid_academy.core.ui.R.font.kanit_regular)),
                fontSize = 12.sp
            )
        }
    }
}

@Preview
@Composable
private fun HomeContentGameCardPreview() {
    HomeContentGameCard(
        gameInfo = GameInfo(
            id = "ab",
            imageRes = R.drawable.game_four_logo,
            description = "Testez votre vocabulaire ! Trouvez le mot caché avant que le pendu ne tombe ...",
            name = "LE JEU DU PENDU"
        ),
        selectedGameId = "",
        {_-> }
    )
}

data class GameInfo(
    var id : String,
    var name : String,
    val description: String,
    @DrawableRes val imageRes: Int
)

