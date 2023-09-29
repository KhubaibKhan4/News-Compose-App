package com.codespacepro.newscomposeapp.navigation.screen.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.codespacepro.newscomposeapp.R

@Composable
fun DetailScreen(
    navController: NavHostController,
    title: String?,
    content: String?,
    imageUrl: String?,
    pubDate: String?,
    creator: String?,
) {

    DetailItem(
        navController, title, content, imageUrl, pubDate, creator
    )


}

@Composable
fun DetailItem(
    navController: NavHostController,
    title: String?,
    content: String?,
    imageUrl: String?,
    pubDate: String?,
    creator: String?,
) {
    val state = rememberScrollState()
    var onClick by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = state),
        verticalArrangement = Arrangement.Top
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
        ) {
            // Image
            AsyncImage(
                model = "$imageUrl",
                contentDescription = "image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Back Button
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp)
                    .align(alignment = Alignment.TopStart)
                    .size(32.dp)
                    .toggleable(
                        value = onClick,
                        interactionSource = MutableInteractionSource(),
                        indication = null,
                        role = Role.Button,
                        onValueChange = { onClick = !onClick }
                    ),

                ) {
                Icon(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = null,
                )
            }

            // Back Button
            IconButton(
                onClick = { /* Handle back button click */ },
                modifier = Modifier
                    .padding(end = 16.dp, top = 16.dp)
                    .align(alignment = Alignment.TopEnd)
                    .size(32.dp)
                    .clip(shape = RoundedCornerShape(12.dp))
                    .background(color = Color(0XFFFF3A44))
                    .toggleable(
                        value = onClick,
                        interactionSource = MutableInteractionSource(),
                        indication = null,
                        role = Role.Button,
                        onValueChange = { onClick = !onClick }
                    ),

                ) {
                Icon(
                    painter = painterResource(id = R.drawable.add_fav),
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }


        // Box for Title, Date, and Published By
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .offset(y = (-85).dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .clip(shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Date
                    Text(
                        text = "$pubDate",
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2E0505)
                        )
                    )

                    // Title
                    Text(
                        text = "$title",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2E0505),
                        ),
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )

                    // Published by
                    Text(
                        text = "Published by ${creator?.get(0)}",
                        style = TextStyle(
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2E0505)
                        )
                    )
                }
            }
        }

        // Card for Content Text (News Article)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .offset(y = (-80).dp)
                .clip(shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
            shape = RoundedCornerShape(0.dp, 0.dp, 24.dp, 24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
        ) {
            Column {
                // Content Text (News Article)
                Text(
                    text = "$content",
                    modifier = Modifier.padding(16.dp),
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 21.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2E0505),
                        textAlign = TextAlign.Justify
                    )
                )
            }

        }

    }

}
