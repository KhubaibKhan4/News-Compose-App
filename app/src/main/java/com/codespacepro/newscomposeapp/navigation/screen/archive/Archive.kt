package com.codespacepro.newscomposeapp.navigation.screen.archive

import android.content.ContentValues
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
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
import com.codespacepro.newscomposeapp.utli.Constant
import com.codespacepro.newscomposeapp.viewmodel.MainViewModel
import java.net.SocketTimeoutException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArchiveScreen(navController: NavHostController) {

    var isLoading by remember {
        mutableStateOf(true)
    }
    val mainViewModel: MainViewModel = MainViewModel(Repository())

    val owner: LifecycleOwner = LocalLifecycleOwner.current
    var data by remember {
        mutableStateOf<News?>(null)
    }


    try {
        mainViewModel.getArchive(apiKey = Constant.API_KEY, country = "us")

        mainViewModel.myArchiveResponse.observe(owner, Observer { response ->
            if (response?.isSuccessful == true) {
                data = response.body()
                Log.d("Archive", "Archive: $data")
                isLoading = false
            }
        })
        // Perform your network request here
    } catch (e: SocketTimeoutException) {
        isLoading = false
        // Log the timeout error for debugging
        Log.e(ContentValues.TAG, "SocketTimeoutException: ${e.message}")
    }


    Scaffold(topBar = {
          TopAppBar(
            title = {
                Text(
                    text = "Archive News",
                    style = TextStyle(
                        fontSize = 17.sp,
                        lineHeight = 22.sp,
                        fontFamily = FontFamily(Font(R.font.sf_pro_text)),
                        fontWeight = FontWeight(600),
                        color = Color(0xFFFF3A44),
                        textAlign = TextAlign.Center,
                    ),
                    modifier = Modifier
                        .width(311.dp),
                )
            },
            navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowLeft,
                        contentDescription = null
                    )
                }
            })
    }, content = {
        Column(modifier = Modifier.padding(top = it.calculateTopPadding())) {
            if (isLoading) {
                Box(contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                data?.let { it1 -> ArchiveList(news = it1, navController) }
            }

        }
    })

}

@Composable
fun ArchiveList(news: News, navController: NavHostController) {
    LazyColumn {
        items(news.results) {
            ArchiveUpdatesItem(result = it, navController)
        }
    }
}


@Composable
fun ArchiveUpdatesItem(result: Result, navController: NavHostController) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 15.dp, end = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        AsyncImage(
            model = result.image_url ?: "${Constant.DEFAULT_IMAGE}",
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .width(345.dp)
                .height(128.dp)
                .clip(shape = RoundedCornerShape(14.dp)),
            placeholder = painterResource(id = R.drawable.world),
            filterQuality = FilterQuality.High,
            fallback = painterResource(id = R.drawable.f)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "${result?.pubDate}",
            style = TextStyle(
                fontSize = 12.sp,
                lineHeight = 20.8.sp,
                fontFamily = FontFamily(Font(R.font.nunito)),
                fontWeight = FontWeight(300),
                color = Color(0xFF2E0505),
            ),
            modifier = Modifier
                .width(345.dp)
                .height(21.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "${result?.title}",
            style = TextStyle(
                fontSize = 14.sp,
                lineHeight = 20.8.sp,
                fontFamily = FontFamily(Font(R.font.new_york_small)),
                fontWeight = FontWeight(600),
                color = Color(0xFF121212),
            ),
            modifier = Modifier
                .width(343.dp)
                .height(42.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = Modifier
                .width(345.dp)
                .clickable {

                    try {
                        navController.navigate(
                            BottomNavScreen.Detail.passData(
                                title = result.title,
                                content = Uri.encode(result.content),
                                imageUrl = Uri.encode(result.image_url ?: Constant.DEFAULT_IMAGE),
                                pubDate = result.pubDate,
                                creator = result.creator
                            )
                        )
                    } catch (e: Exception) {
                        Log.e(ContentValues.TAG, "SocketTimeoutException: ${e.message}")
                    }

                },
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color.Black,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal,
                    )
                ) {
                    append("${result?.description}")
                }

                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                        color = Color(0XFF0080FF)
                    )
                ) {
                    append("Read More")
                }
            },
            maxLines = 4
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Published by ${result.creator?.get(0)}",
            style = TextStyle(
                fontSize = 12.sp,
                lineHeight = 20.8.sp,
                fontFamily = FontFamily(Font(R.font.nunito)),
                fontWeight = FontWeight(700),
                color = Color(0xFF2E0505),
            ),
            modifier = Modifier
                .width(345.dp)
                .height(21.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
    }

}

