package com.codespacepro.newscomposeapp.navigation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.codespacepro.newscomposeapp.navigation.graph.BottomNavScreen


@Composable
fun HomeScreen (navController: NavHostController) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Home", modifier = Modifier.clickable {
            navController.navigate(BottomNavScreen.Profile.route)
        })
    }
}