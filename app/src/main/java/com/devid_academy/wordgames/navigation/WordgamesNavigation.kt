package com.devid_academy.wordgames.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.devid_academy.ui.model.Route
import com.devid_academy.wordgames.navigation.NavigationBuilder.setHangmanScreen
import com.devid_academy.wordgames.navigation.NavigationBuilder.setHomeScreen

@Composable
fun WordgamesNavigation (
    innerPadding: PaddingValues = PaddingValues()
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Route.HomeScreen.name) {
        setHomeScreen(navController, innerPadding)
        setHangmanScreen(navController, innerPadding)
    }
}