package com.codespacepro.newscomposeapp.navigation.screen

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Notifications
import androidx.compose.material.icons.sharp.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.codespacepro.newscomposeapp.R
import com.codespacepro.newscomposeapp.model.News
import com.codespacepro.newscomposeapp.model.Result
import com.codespacepro.newscomposeapp.repository.Repository
import com.codespacepro.newscomposeapp.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(navController: NavHostController) {

    var categoryN by remember {
        mutableStateOf("environment")
    }
    var searchText by remember {
        mutableStateOf("")
    }
    var isLoading by remember {
        mutableStateOf(true)
    }
    var isCategoryLoading by remember {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()

    val mainViewModel: MainViewModel = MainViewModel(Repository())

    val owner: LifecycleOwner = LocalLifecycleOwner.current


    mainViewModel.getNews(
        apiKey = "pub_238458c1ba1e35414e6402b4c551dc42d5af7",
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
            Log.d("Main", "HomeScreen: ${response.body()}")
            isLoading = false
        } else {
            isLoading = false
        }
    })


    val categories = listOf(
        "environment",
        "business",
        "entertainment",
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
    var selectedCategory by remember { mutableStateOf(categories.first()) }
    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Column {
                CircularProgressIndicator()
            }
        }
    } else {
        Scaffold(topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    modifier = Modifier.fillMaxWidth(0.76f),
                    label = {
                        Text(text = "Search News")
                    },
                    trailingIcon = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                imageVector = Icons.Sharp.Search, contentDescription = "search",
                                tint = Color.Black
                            )
                        }
                    },
                    textStyle = TextStyle.Default,
                    placeholder = {
                        Text(text = "Dogecoin to the Moon...")
                    },
                    shape = TextFieldDefaults.outlinedShape,
                    interactionSource = MutableInteractionSource(),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        autoCorrect = true,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(onSearch = {

                    })
                )
                Card(
                    modifier = Modifier
                        .width(40.dp)
                        .height(40.dp)
                        .clip(shape = CircleShape)
                        .background(color = Color(0XFFFF3A44))
                ) {
                    IconButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .clip(shape = CircleShape)
                            .background(color = Color(0XFFFF3A44))
                    ) {
                        Icon(
                            imageVector = Icons.Sharp.Notifications,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            }
        },
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = it.calculateTopPadding()),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TopNews()
                    Spacer(modifier = Modifier.height(15.dp))
                    LazyRow {
                        item {
                            categories.forEach { category ->
                                Box(modifier = Modifier.fillMaxWidth()) {
                                    ElevatedButton(
                                        onClick = {
                                            isCategoryLoading = true
                                            scope.launch(Dispatchers.IO) {
                                                mainViewModel.getNews(
                                                    "pub_238458c1ba1e35414e6402b4c551dc42d5af7",
                                                    query = "developer",
                                                    country = "us",
                                                    category = category
                                                )
                                                selectedCategory = category
                                                delay(500)
                                                isCategoryLoading = false
                                            }

                                            Log.d("main", "Categories: $category")
                                        },
                                        modifier = Modifier.padding(4.dp),
                                        shape = RoundedCornerShape(12.dp),
                                        colors = ButtonDefaults.elevatedButtonColors(
                                            containerColor = if (selectedCategory == category) Color(
                                                0XFFFF3A44
                                            ) else Color.White
                                        )
                                    ) {
                                        Text(
                                            text = category.uppercase(Locale.ROOT),
                                            color = if (selectedCategory == category) Color.White else Color.Black
                                        )
                                    }
                                }
                            }
                        }
                    }
                    data?.results?.let { ArticleList(articles = it, isCategoryLoading) }
                }
            })
    }
}

@Composable
fun ArticleList(articles: List<Result>, isLoading: Boolean) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(articles) { article ->
            ArticleCard(article, isLoading)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}


@Composable
fun ArticleCard(article: Result, isLoading: Boolean) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var visibility by remember {
        mutableStateOf(false)
    }
    if (isLoading) {
        Box(contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        Card(
            modifier = Modifier
                .width(345.dp)
                .height(128.dp)
                .padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 8.dp)
                .clickable {
                    visibility = true
                }
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(article.image_url)
                        .crossfade(enable = true)
                        .placeholder(R.drawable.world)
                        .build(),
                    placeholder = painterResource(id = R.drawable.world),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop,
                )
                Text(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(start = 4.dp, end = 4.dp),
                    text = article.title,
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 20.8.sp,
                        fontFamily = FontFamily(Font(R.font.new_york_small)),
                        fontWeight = FontWeight(600),
                        color = Color(0xFFFFFFFF),
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center
                )
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 8.dp, end = 8.dp),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${article?.creator?.get(0)}",
                        style = TextStyle(
                            fontSize = 12.sp,
                            lineHeight = 20.8.sp,
                            fontFamily = FontFamily(Font(R.font.nunitto)),
                            fontWeight = FontWeight(600),
                            color = Color(0xFFFFFFFF),
                        )
                    )

                    Text(
                        text = "${article.pubDate}",
                        style = TextStyle(
                            fontSize = 12.sp,
                            lineHeight = 20.8.sp,
                            fontFamily = FontFamily(Font(R.font.nunitto)),
                            fontWeight = FontWeight(600),
                            color = Color(0xFFFFFFFF),
                            textAlign = TextAlign.Right,
                        )
                    )
                }

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
}



