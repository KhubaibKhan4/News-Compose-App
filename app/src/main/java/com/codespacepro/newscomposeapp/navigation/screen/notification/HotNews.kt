package com.codespacepro.newscomposeapp.navigation.screen.notification

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.codespacepro.newscomposeapp.R

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HotNews(navController: NavHostController) {

    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    text = "Hot Updates",
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
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowLeft,
                        contentDescription = null
                    )
                }
            })
    }, content = {
        LazyColumn(modifier = Modifier.padding(top = it.calculateTopPadding())) {
            item {
                repeat(8) {
                    HotUpdatesItem()
                }
            }
        }
    })

}


@Composable
fun HotUpdatesItem() {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 15.dp, end = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(8.dp))
//        AsyncImage(
//            model = "",
//            contentDescription = null,
//            modifier = Modifier
//                .width(345.dp)
//                .height(128.dp)
//                .padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 8.dp)
//        )
        Image(
            painter = painterResource(id = R.drawable.f), contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .width(345.dp)
                .height(128.dp)
                .clip(shape = RoundedCornerShape(14.dp))
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Monday, 10 May 2021",
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
            text = "WHO classifies triple-mutant Covid variant from India as global health risk",
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
        Text(modifier = Modifier
            .width(345.dp),
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color.Black,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal,
                    )
                ) {
                    append("A World Health Organization official said Monday it is reclassifying the highly contagious triple-mutant Covid variant spreading in India as a “variant of concern,” indicating that it’s become a...")
                }

                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                        color = Color(0XFF0080FF)
                    )
                ) {
                    append("Read More")
                }
            })
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Published by Berkeley Lovelace Jr.",
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

@Preview(showBackground = true)
@Composable
fun Preview() {
    HotUpdatesItem()
}