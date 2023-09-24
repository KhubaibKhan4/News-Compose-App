package com.codespacepro.newscomposeapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Observer
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.codespacepro.newscomposeapp.model.News
import com.codespacepro.newscomposeapp.model.Result
import com.codespacepro.newscomposeapp.navigation.screen.MainScreen
import com.codespacepro.newscomposeapp.repository.Repository
import com.codespacepro.newscomposeapp.ui.theme.NewsComposeAppTheme
import com.codespacepro.newscomposeapp.viewmodel.MainViewModel
import com.codespacepro.newscomposeapp.viewmodel.MainViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var mainViewModel: MainViewModel

    @Inject
    lateinit var repository: Repository

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsComposeAppTheme {
                var isLoading by remember {
                    mutableStateOf(true)
                }
                var newsSate by remember {
                    mutableStateOf<News?>(null)
                }
                var resultState by remember {
                    mutableStateOf<List<Result?>>(emptyList())
                }
                var error by remember {
                    mutableStateOf(false)
                }

                val context = LocalContext.current
                mainViewModel.getNews(
                    "pub_238458c1ba1e35414e6402b4c551dc42d5af7",
                    query = "developer",
                    country = "us",
                    category = "science"
                )

                mainViewModel.myResponse.observe(this, Observer { response ->
                    if (response.isSuccessful) {
                        error = false
                        isLoading = false
                        newsSate = response.body()
                        resultState = response.body()?.results ?: emptyList()
                        Log.d("main", resultState.toString())
                    } else {
                        error = true
                        isLoading = false
                        Toast.makeText(context, response.code().toString(), Toast.LENGTH_SHORT)
                            .show()
                    }
                })

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    if (isLoading) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    } else {
//                            LazyHorizontalGrid(rows = GridCells.Fixed(1)) {
//                                items(resultState) {
//                                    it?.let { it1 -> NewsItem(result = it1) }
//                                }
//                            }
                        LazyColumn {
                            items(resultState) { result ->
                                result?.let { NewsItem(result = it) }
                            }
                        }

                    }



                    MainScreen()
                }
            }
        }
    }
}


@Composable
fun NewsItem(result: Result) {
    val context = LocalContext.current
    val navController = rememberNavController()
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(4.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),

            ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .width(200.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(result.image_url)
                        .crossfade(true)
                        .placeholder(R.drawable.placeholder)
                        .build(),
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(10.dp)),
                    contentDescription = null,
                    placeholder = painterResource(id = R.drawable.placeholder),


                    )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = result.title ?: "No Title",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isSystemInDarkTheme()) Color.White else Color.Black
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = result.content ?: "No Content",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray
                ),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = result.pubDate ?: "No Date",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary else Color.Blue
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NewsComposeAppTheme {

    }
}