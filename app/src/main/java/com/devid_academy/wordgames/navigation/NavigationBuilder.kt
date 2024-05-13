package com.devid_academy.wordgames.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.devid_academy.hangman.HangmanScreen
import com.devid_academy.home.HomeScreen
import com.devid_academy.ui.model.Route


object NavigationBuilder {

    fun NavGraphBuilder.setHomeScreen(
        navController: NavHostController,
        innerPadding: PaddingValues = PaddingValues(),
        animationDuration: Int = 700
    ) {
        composable(
            route = Route.HomeScreen.name
        ){
            HomeScreen(navController, innerPadding)
        }
    }

    fun NavGraphBuilder.setHangmanScreen(
        navController: NavHostController,
        innerPadding: PaddingValues = PaddingValues(),
        animationDuration: Int = 700
    ) {
        composable(
            route = Route.HangmanScreen.name
        ){
            HangmanScreen(navController, innerPadding)
        }
    }

}