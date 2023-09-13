package com.codespacepro.newscomposeapp.navigation.graph

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomNavScreen(
        route = "home",
        title = "Home",
        icon = Icons.Default.Home
    )

    object Search : BottomNavScreen(
        route = "search",
        title = "Search",
        icon = Icons.Default.Search
    )

    object Profile : BottomNavScreen(
        route = "profile",
        title = "Profile",
        icon = Icons.Default.Person
    )
}
