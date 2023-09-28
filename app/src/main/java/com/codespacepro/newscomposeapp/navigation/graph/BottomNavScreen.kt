package com.codespacepro.newscomposeapp.navigation.graph

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
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
    object TopNews: BottomNavScreen(
        route = "top",
        title = "Top News",
        icon = Icons.Default.MoreVert
    )

    object Detail : BottomNavScreen(
        route = "detail/{title}/{content}/{pubDate}/{creator}/{imageUrl}",
        title = "Details",
        icon = Icons.Default.MoreVert
    ) {
        fun passData(
            title: String?,
            content: String?,
            pubDate: String?,
            creator: List<String>?,
            imageUrl: String?,
        ): String {
            return "detail/$title/$content/$pubDate/$creator/$imageUrl"
        }
    }


    object HotNews : BottomNavScreen(
        route = "hot_news",
        title = "HotNews",
        icon = Icons.Default.MoreVert
    )
}
