package com.devid_academy.wordgames.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.devid_academy.common.auth.login.LoginScreen
import com.devid_academy.common.auth.singup.SignupScreen
import com.devid_academy.common.home.HomeScreen
import com.devid_academy.common.leaderboard.Leaderboard
import com.devid_academy.common.profile.Profile
import com.devid_academy.common.splash.SplashScreen
import com.devid_academy.hangman.HangmanScreen
import com.devid_academy.motus.MotusScreen
import com.devid_academy.ui.Route


object NavigationBuilder {

    fun NavGraphBuilder.setSplashScreen(
        navController: NavHostController,
        innerPadding: PaddingValues = PaddingValues(),
        animationDuration: Int = 700
    ) {
        composable(
            route = Route.SplashScreen.name
        ){
            SplashScreen(navController, innerPadding)
        }
    }


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


    fun NavGraphBuilder.setMotusScreen(
        navController: NavHostController,
        innerPadding: PaddingValues = PaddingValues(),
        animationDuration: Int = 700
    ) {
        composable(
            route = Route.MotusScreen.name
        ){
            MotusScreen(navController, innerPadding)
        }
    }

    fun NavGraphBuilder.setLoginScreen(
        navController: NavHostController,
        innerPadding: PaddingValues = PaddingValues(),
        animationDuration: Int = 700
    ) {
        composable(
            route = Route.LoginScreen.name
        ){
            LoginScreen(navController)
        }
    }

    fun NavGraphBuilder.setSignupScreen(
        navController: NavHostController,
        innerPadding: PaddingValues = PaddingValues(),
        animationDuration: Int = 700
    ) {
        composable(
            route = Route.SignupScreen.name
        ){
            SignupScreen(navController)
        }
    }

    fun NavGraphBuilder.setLeaderboardScreen(
        navController: NavHostController,
        innerPadding: PaddingValues = PaddingValues(),
        animationDuration: Int = 700
    ) {
        composable(
            route = Route.LeaderboardScreen.name
        ){
            Leaderboard(navController)
        }
    }

    fun NavGraphBuilder.setProfileScreen(
        navController: NavHostController,
        innerPadding: PaddingValues = PaddingValues(),
        animationDuration: Int = 700
    ) {
        composable(
            route = Route.ProfileScreen.name
        ){
            Profile(navController)
        }
    }
}