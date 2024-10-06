package com.example.almate.features.home.presentation

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.almate.presentation.theme.cardBackgroundColor
import com.valentinilk.shimmer.shimmer

@Composable
fun HomeSkeletonScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .verticalScroll(ScrollState(0))
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 24.dp)
            .shimmer()
    ) {

        // user info
        Row {
            Box(
                modifier = Modifier
                    .size(82.dp)
                    .clip(CircleShape)
                    .background(cardBackgroundColor)
            )
            Spacer(Modifier.width(16.dp))
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.height(82.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(height = 20.dp, width = 140.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(cardBackgroundColor)
                )
                Box(
                    modifier = Modifier
                        .size(height = 24.dp, width = 80.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(cardBackgroundColor)
                )
                Box(
                    modifier = Modifier
                        .size(height = 16.dp, width = 100.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(cardBackgroundColor)
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        // gpa analytics
        Column {
            Box(
                modifier = Modifier
                    .size(height = 20.dp, width = 100.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(cardBackgroundColor)
            )
            Spacer(Modifier.height(8.dp))
            Row {
                Box(
                    modifier = Modifier
                        .height(94.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(cardBackgroundColor)
                        .weight(1f)
                )
                Spacer(Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .height(94.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(cardBackgroundColor)
                        .weight(1f)
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        // grades
        Column {
            Box(
                modifier = Modifier
                    .size(height = 20.dp, width = 100.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(cardBackgroundColor)
            )
            Spacer(Modifier.height(8.dp))
            for (i in 1..10) {
                Box(
                    modifier = Modifier
                        .height(118.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(cardBackgroundColor)
                        .fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
            }
        }

    }
}