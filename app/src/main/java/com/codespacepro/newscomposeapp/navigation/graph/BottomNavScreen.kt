package com.codespacepro.newscomposeapp.navigation.graph

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.sharp.FavoriteBorder
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

    object Crypto : BottomNavScreen(
        route = "crypto",
        title = "Crypto",
        icon = Icons.Default.Warning
    )

    object Archive : BottomNavScreen(
        route = "archive",
        title = "Archive",
        icon = Icons.Default.Email
    )
    object Favourite : BottomNavScreen(
        route = "favourite",
        title = "Favourite",
        icon = Icons.Sharp.FavoriteBorder
    )

    object TopNews : BottomNavScreen(
        route = "top",
        title = "Top News",
        icon = Icons.Default.MoreVert
    )

    object Detail : BottomNavScreen(
        route = "detail/{title}/{content}/{imageUrl}/{pubDate}/{creator}",
        title = "Details",
        icon = Icons.Default.MoreVert
    ) {
        fun passData(
            title: String?,
            content: String?,
            imageUrl: String?,
            pubDate: String?,
            creator: List<String>?
        ): String {
            return "detail/$title/$content/$imageUrl/$pubDate/$creator"
        }
    }


    object HotNews : BottomNavScreen(
        route = "hot_news",
        title = "HotNews",
        icon = Icons.Default.MoreVert
    )
}
