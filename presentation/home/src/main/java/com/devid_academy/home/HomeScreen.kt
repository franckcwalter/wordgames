package com.devid_academy.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.devid_academy.ui.model.Route

@Composable
fun HomeScreen(
    navController: NavHostController,
    innerPadding: PaddingValues
){
    HomeContent(
        innerPadding = innerPadding,
        onHangmanButtonClick = {
            navController.navigate(Route.HangmanScreen.name)
        }
    )
}

@Composable
fun HomeContent(
    innerPadding: PaddingValues,
    onHangmanButtonClick: ()->Unit
){
    Box(modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier.align(Alignment.Center)) {
            Text("Home")
            Button( onHangmanButtonClick ){
                Text("To hangman")
            }
        }
    }
}