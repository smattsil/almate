package com.example.almate.features.profile.presentation

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.almate.presentation.theme.cardBackgroundColor
import com.valentinilk.shimmer.shimmer

@Composable
fun ProfileSkeletonScreen(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(state = ScrollState(0), enabled = false)
            .padding(horizontal = 16.dp, vertical = 24.dp)
            .shimmer()
    ) {

        // user info
        Box(
            modifier = Modifier
                .size(82.dp)
                .clip(CircleShape)
                .background(cardBackgroundColor)
        )

        Spacer(Modifier.height(24.dp))

        // general
        Column {
            Box(
                modifier = Modifier
                    .size(height = 20.dp, width = 100.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(cardBackgroundColor)
            )
            Spacer(Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .height(164.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(cardBackgroundColor)
            )
        }

        Spacer(Modifier.height(24.dp))

        // attendances
        Column {
            Box(
                modifier = Modifier
                    .size(height = 20.dp, width = 100.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(cardBackgroundColor)
            )
            Spacer(Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .height(164.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(cardBackgroundColor)
            )
        }

        Spacer(Modifier.height(24.dp))

        // log out button
        Box(
            modifier = Modifier
                .height(36.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(cardBackgroundColor)
        )

    }
}