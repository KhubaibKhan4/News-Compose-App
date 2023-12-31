package com.codespacepro.newscomposeapp.navigation.screen.home

import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.codespacepro.newscomposeapp.R
import com.codespacepro.newscomposeapp.model.News
import com.codespacepro.newscomposeapp.model.Result
import com.codespacepro.newscomposeapp.navigation.graph.BottomNavScreen
import com.codespacepro.newscomposeapp.repository.Repository
import com.codespacepro.newscomposeapp.utli.Constant.Companion.API_KEY
import com.codespacepro.newscomposeapp.utli.Constant.Companion.DEFAULT_IMAGE
import com.codespacepro.newscomposeapp.viewmodel.MainViewModel
import java.net.SocketTimeoutException

@Composable
fun TopNews(navController: NavHostController) {

    val mainViewModel: MainViewModel = MainViewModel(Repository())

    val owner: LifecycleOwner = LocalLifecycleOwner.current
    var data by remember {
        mutableStateOf<News?>(null)
    }



    try {
        mainViewModel.getNews(
            apiKey = API_KEY,
            query = "developer",
            country = "us",
            category = "world"
        )



        mainViewModel.myResponse.observe(owner, Observer { response ->
            if (response?.isSuccessful == true) {
                data = response.body()
            }
        })
        // Perform your network request here
    } catch (e: SocketTimeoutException) {
        // Log the timeout error for debugging
        Log.e(TAG, "SocketTimeoutException: ${e.message}")
    }


    Column {
        LatestNewsText()
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(start = 8.dp, top = 8.dp)
        ) {
            data?.let {
                items(it.results) { result ->

                    TopNewsCard(result, navController)
                    Spacer(modifier = Modifier.width(16.dp))
                }
            }
        }
    }
}


@Composable
fun TopNewsCard(result: Result, navController: NavHostController) {

    Card(
        modifier = Modifier
            .width(345.dp)
            .height(240.dp)
            .clickable {
                navController.navigate(
                    BottomNavScreen.Detail.passData(
                        title = Uri.encode(result.title),
                        content = Uri.encode(result.content),
                        imageUrl = Uri.encode(result.image_url ?: DEFAULT_IMAGE),
                        pubDate = result.pubDate,
                        creator = result.creator
                    )
                )
            },
        colors = CardDefaults.cardColors()
    ) {
        Box {

            AsyncImage(
                model = result.image_url ?: DEFAULT_IMAGE,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.FillBounds,
                placeholder = painterResource(id = R.drawable.world),
                filterQuality = FilterQuality.High,
                fallback = painterResource(id = R.drawable.f)
            )


            Text(
                text = result.title,
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 20.8.sp,
                    fontFamily = FontFamily(Font(R.font.new_york_small)),
                    fontWeight = FontWeight(700),
                    color = Color(0xFFFFFFFF),
                ),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(start = 4.dp, end = 4.dp, bottom = 4.dp),
                textAlign = TextAlign.Center,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

        }
    }
}

@Composable
fun LatestNewsText() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 6.dp, end = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(0.75f),
            text = "Latest News",
            style = TextStyle(
                fontSize = 18.sp,
                lineHeight = 20.8.sp,
                fontFamily = FontFamily(Font(R.font.new_york_small)),
                fontWeight = FontWeight(700),
                color = Color(0xFF000000),
            )
        )
        Text(
            text = "See All",
            style = TextStyle(
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.nunito)),
                fontWeight = FontWeight(600),
                color = Color(0xFF0080FF),
            )
        )
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Sharp.KeyboardArrowRight,
                contentDescription = "forward",
                tint = Color(0xFF0080FF),
            )
        }


    }
}