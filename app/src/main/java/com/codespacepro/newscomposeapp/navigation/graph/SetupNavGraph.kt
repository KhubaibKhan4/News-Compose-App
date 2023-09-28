package com.codespacepro.newscomposeapp.navigation.graph

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.codespacepro.newscomposeapp.navigation.screen.detail.DetailScreen
import com.codespacepro.newscomposeapp.navigation.screen.home.HomeScreen
import com.codespacepro.newscomposeapp.navigation.screen.home.TopNews
import com.codespacepro.newscomposeapp.navigation.screen.notification.HotNews
import com.codespacepro.newscomposeapp.navigation.screen.profile.ProfileScreen
import com.codespacepro.newscomposeapp.navigation.screen.search.SearchScreen

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
        composable(route = BottomNavScreen.HotNews.route) {
            HotNews(navController)
        }
        composable(route = BottomNavScreen.TopNews.route){
            TopNews(navController = navController)
        }

        composable(
            route = BottomNavScreen.Detail.route,
            arguments = listOf(
                navArgument(name = "title") {
                    type = NavType.StringType
                },
                navArgument(name = "content") {
                    type = NavType.StringType
                },
                navArgument(name = "pubDate") {
                    type = NavType.StringType
                },
                navArgument(name = "creator") {
                    type = NavType.StringType
                },
                navArgument(name = "imageUrl") {
                    type = NavType.StringType
                },
            )
        ) {
            val title = it.arguments?.getString("title")
            val content = it.arguments?.getString("content")
            val pubDate = it.arguments?.getString("pubDate")
            val creator = it.arguments?.getString("creator")
            val imageUrl = it.arguments?.getString("imageUrl")

            Log.d(
                "NAV_ARGUMENTS",
                "SetupNavGraph: $title \n $content \n  \n $pubDate \n $creator \n  $imageUrl"
            )
            DetailScreen(navController = navController, title, content, pubDate, creator,imageUrl)
        }

    }

}