package com.codespacepro.newscomposeapp.navigation.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.codespacepro.newscomposeapp.navigation.screen.HomeScreen
import com.codespacepro.newscomposeapp.navigation.screen.ProfileScreen
import com.codespacepro.newscomposeapp.navigation.screen.SearchScreen

@Composable
fun SetupNavGraph(navController: NavHostController) {

    NavHost(navController = navController, startDestination = BottomNavScreen.Home.route) {
        composable(route = BottomNavScreen.Home.route) {
            HomeScreen(navController)
        }

        composable(route = BottomNavScreen.Search.route) {
            SearchScreen(navController)
        }
        composable(route = BottomNavScreen.Profile.route) {
            ProfileScreen(navController)
        }
    }

}