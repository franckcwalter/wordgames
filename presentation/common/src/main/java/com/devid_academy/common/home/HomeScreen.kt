package com.devid_academy.common.home

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.annotation.DrawableRes
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Public
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.devid_academy.common.R
import com.devid_academy.common.common.LargeButton
import com.devid_academy.common.common.MediumButton
import com.devid_academy.common.common.Background
import com.devid_academy.common.common.SquareIconButton
import com.devid_academy.common.game_base.QuitGameModale
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

    BackHandler(onBack = { viewModel.toggleQuitAppModal() })

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

    val activity : Activity = LocalContext.current as Activity
    if (homeUiState.value.isDisplayingQuitApp){
        QuitGameModale(
            onStay = { viewModel.toggleQuitAppModal() },
            onQuit = {
                // TODO : afficher animation

                activity.finish()
                     },
            stayButtonLabel = "RETOUR À WORDGAMES",
            quitButtonLabel = "Quitter WORDGAMES",
            text = "Voulez-vous vraiment\nquitter WORDGAMES? "
        )
    }
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
    Box(modifier = Modifier.fillMaxWidth()){

        Background()

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
                
                SquareIconButton(
                    imageVector = Icons.Rounded.Public,
                    modifier = Modifier
                        .padding(end = 12.dp)
                        .align(Alignment.CenterEnd)){
                    onOfflineOnline()
                }
            }

            Spacer(Modifier.height(24.dp))

            Text(
                text = "Choisissez un jeu\n et commencez à jouer !",
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(24.dp))

            Box(Modifier.weight(.1f).verticalScroll(rememberScrollState())){
                Column(Modifier){
                    homeUiState.gameInfoList.forEach {
                        HomeContentGameCard(
                            gameInfo = it,
                            selectedGameId = homeUiState.selectedGameId,
                            onGameCard = { selectedGameId ->
                                onGameCard(selectedGameId)
                            }
                        )
                    }
                }
            }
            Spacer(Modifier.height(24.dp))

            LargeButton(
                label = "COMMENCER À JOUER !",
                containerColor = Color(0xFFfdb420)){
                onStartGame()
            }

            Spacer(Modifier.height(12.dp))

            Row() {
                MediumButton(
                    label = "Statistiques",
                    containerColor = Color(0xFFfe6465)) {
                    onStats()
                }
                Spacer(Modifier.width(12.dp))
                MediumButton(
                    label = "Profile",
                    containerColor = Color(0xFF4de5d0)) {
                    onProfile()
                }
            }

            Spacer(Modifier.height(24.dp))

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
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text = gameInfo.description,
                style = MaterialTheme.typography.bodyMedium,
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


@Preview
@Composable
private fun HomeContentPreview() {
    HomeContent(
        homeUiState = HomeUiState(),
        onProfile = {},
        onStats = {},
        onStartGame = {},
        onGameCard = {},
        onOfflineOnline = {}
    )
}


