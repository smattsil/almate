package com.example.almate.features.home.presentation

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.almate.presentation.theme.cardBackgroundColor
import com.valentinilk.shimmer.shimmer

@Composable
fun SubjectSkeletonScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(state = ScrollState(0), enabled = false)
            .padding(horizontal = 16.dp, vertical = 24.dp)
            .clip(RectangleShape)
            .shimmer()
    ) {

        // overall grade
        Column {
            Box(
                modifier = Modifier
                    .size(height = 20.dp, width = 100.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(cardBackgroundColor)
            )
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier.height(IntrinsicSize.Min)
            ) {
                Box(
                    modifier = Modifier
                        .width(74.dp)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(12.dp))
                        .background(cardBackgroundColor)
                )
                Spacer(Modifier.width(8.dp))
                Column {
                    for (i in 1..5) {
                        Spacer(Modifier.height(8.dp))
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Column {
                                Box(
                                    modifier = Modifier
                                        .size(height = 16.dp, width = 100.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(cardBackgroundColor)
                                )
                                Spacer(Modifier.height(4.dp))
                                Box(
                                    modifier = Modifier
                                        .size(height = 12.dp, width = 48.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(cardBackgroundColor)
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .size(height = 20.dp, width = 50.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(cardBackgroundColor)
                            )
                        }
                        Spacer(Modifier.height(8.dp))
                    }
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        // assignments
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
                        .height(90.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(cardBackgroundColor)
                        .fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
            }
        }

    }
}
