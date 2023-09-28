package com.codespacepro.newscomposeapp.navigation.screen.home

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CategoryShimmerEffect() {

    val shimmer = listOf(
        Color.LightGray.copy(0.6f),
        Color.LightGray.copy(0.2f),
        Color.LightGray.copy(0.6f),
    )

    val transition = rememberInfiniteTransition(label = "Transition")
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 1000, easing = FastOutSlowInEasing)
        ), label = "Translate Anim"
    )

    val brush = Brush.linearGradient(
        colors = shimmer,
        start = Offset.Zero,
        end = Offset(x = translateAnim.value, y = translateAnim.value)
    )

    CategoryShimmerGridItem(brush = brush)

}

@Composable
fun CategoryShimmerGridItem(brush: Brush) {

    Card(
        modifier = Modifier
            .width(345.dp)
            .height(128.dp)
            .padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 8.dp),
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(brush),
            )
            Spacer(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .background(brush)
                    .padding(start = 4.dp, end = 4.dp),
            )
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(brush = brush)
                    .padding(start = 8.dp, end = 8.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(brush)
                )

                Spacer(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(brush)
                )
            }

        }
    }
}