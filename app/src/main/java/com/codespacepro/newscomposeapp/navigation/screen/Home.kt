package com.codespacepro.newscomposeapp.navigation.screen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.codespacepro.newscomposeapp.model.News
import com.codespacepro.newscomposeapp.model.Result
import com.codespacepro.newscomposeapp.repository.Repository
import com.codespacepro.newscomposeapp.viewmodel.MainViewModel
import java.util.Locale


@Composable
fun HomeScreen(navController: NavHostController) {

    val scope = rememberCoroutineScope()
    var categoryN by remember {
        mutableStateOf("science")
    }

    val mainViewModel: MainViewModel = MainViewModel(Repository())

    val owner: LifecycleOwner = LocalLifecycleOwner.current


    mainViewModel.getNews(
        "pub_238458c1ba1e35414e6402b4c551dc42d5af7",
        query = "developer",
        country = "us",
        category = categoryN
    )

    var data by remember {
        mutableStateOf<News?>(null)
    }

    mainViewModel.myResponse.observe(owner, Observer { response ->
        if (response?.isSuccessful == true) {
            data = response.body()
        }
    })


    val categories = listOf(
        "business",
        "entertainment",
        "environment",
        "food",
        "health",
        "politics",
        "science",
        "sports",
        "technology",
        "top",
        "tourism",
        "world"
    )



    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LazyRow {
            item {
                categories.forEach {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        ElevatedButton(
                            onClick = {
                                mainViewModel.getNews(
                                    "pub_238458c1ba1e35414e6402b4c551dc42d5af7",
                                    query = "developer",
                                    country = "us",
                                    category = "$it"
                                )
                                Log.d("main", "Categories: $it")
                            },
                            modifier = Modifier.padding(4.dp)
                        ) {
                            Text(text = it.uppercase(Locale.ROOT))
                        }
                    }
                }
            }
        }

        data?.results?.let {
            mainViewModel.getNews(
                "pub_238458c1ba1e35414e6402b4c551dc42d5af7",
                query = "developer",
                country = "us",
                category = "top"
            )
            TopNewsRow(topNews = it)
        }

        Spacer(modifier = Modifier.height(8.dp))
        Divider(
            modifier = Modifier.fillMaxWidth(0.75f),
            thickness = 3.dp,
            color = Color.DarkGray
        )
        data?.results?.let { ArticleList(articles = it) }


    }
}

@Composable
fun Categories(categories: String) {
    Box(modifier = Modifier.fillMaxWidth()) {
        ElevatedButton(
            onClick = {
                Log.d("main", "Categories: $categories")
            },
            modifier = Modifier.padding(4.dp)
        ) {
            Text(text = "$categories")
        }
    }
}

@Composable
fun ArticleList(articles: List<Result>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(articles) { article ->
            ArticleCard(article)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}


@Composable
fun ArticleCard(article: Result) {

    var visibility by remember {
        mutableStateOf(false)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable {
                visibility = true
            }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Display article title
            Text(
                text = article.title,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            // Display article description
            Text(
                text = article.description ?: "",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            // Display article image (if available)
            article.image_url?.let { imageUrl ->
                AsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )
            }

            // Display article link (you can make it clickable)
            Text(
                text = article.link,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Blue,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
        }
        if (visibility) {
            AlertDialog(
                onDismissRequest = { },
                confirmButton = {
                    TextButton(onClick = { visibility = false }) {
                        Text(text = "Confirm")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { visibility = false }) {
                        Text(text = "Dismiss")
                    }
                },
                icon = {
                    AsyncImage(model = article.image_url, contentDescription = null)
                },
                shape = AlertDialogDefaults.shape,
                properties = DialogProperties(
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true,
                    securePolicy = SecureFlagPolicy.Inherit,
                    usePlatformDefaultWidth = true
                ),
                text = {
                    Text(text = article.title)
                }
            )
        }

    }
}


@Composable
fun TopNewsRow(topNews: List<Result>) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        items(topNews) { news ->
            TopNewsCard(news)
            Spacer(modifier = Modifier.width(16.dp))
        }
    }
}

@Composable
fun TopNewsCard(news: Result) {
    Card(
        modifier = Modifier
            .width(200.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            news.image_url?.let { imageUrl ->
                AsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Text(
                text = news.title,
                textAlign = TextAlign.Center
            )
        }
    }
}
