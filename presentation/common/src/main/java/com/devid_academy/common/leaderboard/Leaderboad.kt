package com.devid_academy.common.leaderboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.devid_academy.common.common.Background
import com.devid_academy.common.home.HomeViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun Leaderboard(
    navController: NavHostController,
){

    val viewModel = getViewModel<LeaderboardViewModel>()
    val leaderboardUiState = viewModel.observeUiState().collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getLeaderboard()
    }

    LeaderboardContent(
        leaderboardUiState.value
    )

}

@Composable
fun LeaderboardContent(
    uiState: LeaderboardUiState
) {

    Box(modifier = Modifier.fillMaxWidth()) {

        Background()

        Column(Modifier.align(Alignment.Center)) {



            uiState.leaderboard?.let {


                Spacer(Modifier.weight(1f))
                Text(
                    text = "TABLEAU DE SCORES",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Mes scores",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )

                Row(){
                    Text(
                        text = "Aujourd'hui : ",
                        fontWeight = FontWeight.Bold
                    )
                    Text("${uiState.leaderboard.myPoints.today}")
                }

                Row(){
                    Text(
                        text = "Cette semaine : ",
                        fontWeight = FontWeight.Bold
                    )
                    Text("${uiState.leaderboard.myPoints.thisWeek} points")
                }

                Row(){
                    Text(
                        text = "Total : ",
                        fontWeight = FontWeight.Bold
                    )
                    Text("${uiState.leaderboard.myPoints.allTime} points")
                }

                Spacer(Modifier.height(50.dp))


                Text(
                    text = "Classement",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Aujourd'hui : ",
                    fontWeight = FontWeight.Bold
                )

                uiState.leaderboard.leaderboard.today.forEach { leaderboardEntry ->
                    Text("${leaderboardEntry.username} : ${leaderboardEntry.totalPoints}")
                }

                Spacer(Modifier.height(20.dp))
                Text(
                    text = "Cette semaine : ",
                    fontWeight = FontWeight.Bold
                )

                uiState.leaderboard.leaderboard.thisWeek.forEach { leaderboardEntry ->
                    Text("${leaderboardEntry.username} : ${leaderboardEntry.totalPoints}")
                }
                Spacer(Modifier.height(20.dp))
                Text(
                    text = "Total : ",
                    fontWeight = FontWeight.Bold
                )

                uiState.leaderboard.leaderboard.allTime.forEach { leaderboardEntry ->
                    Text("${leaderboardEntry.username} : ${leaderboardEntry.totalPoints} :")
                }


                Spacer(Modifier.weight(1f))



            }
        }
    }
}

@Preview
@Composable
private fun LeaderboardPreview() {
    LeaderboardContent(LeaderboardUiState())
}