package com.codespacepro.newscomposeapp.navigation.component

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.codespacepro.newscomposeapp.navigation.graph.BottomNavScreen
import com.codespacepro.newscomposeapp.navigation.graph.SetupNavGraph

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(bottomBar = { BottomBar(navController = navController) },
        content = {
            SetupNavGraph(navController = navController)
        })
}

@Composable
fun BottomBar(navController: NavHostController) {

    val screens = listOf(
        BottomNavScreen.Home,
        BottomNavScreen.Crypto,
        BottomNavScreen.Archive,
        BottomNavScreen.Favourite
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        screens.forEach { screens ->
            if (currentDestination != null) {
                AddItem(
                    screen = screens,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
        }
    }

}

@Composable
fun RowScope.AddItem(
    screen: BottomNavScreen,
    currentDestination: NavDestination,
    navController: NavHostController
) {
    NavigationBarItem(
        selected = currentDestination.hierarchy?.any {
            it.route == screen.route
        } == true,
        onClick = {
            navController.navigate(route = screen.route) {
                popUpTo(navController.graph.id)
                launchSingleTop = true
            }
        },
        icon = { Image(imageVector = screen.icon, contentDescription = "") },
        label = { Text(text = screen.title) }
    )

}