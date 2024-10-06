package com.example.almate.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.almate.R
import com.example.almate.presentation.theme.appBarColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlmateTopAppBar(
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .animateContentSize()
    ) {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(containerColor = appBarColor),
            title = {
                Icon(
                    painterResource(R.drawable.almate),
                    contentDescription = null,
                    modifier = Modifier.size(90.dp)
                )
            }
        )
        if (isLoading) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
//        Box(
//            modifier = Modifier
//                .height(1.dp)
//                .background(topAppBarStrokeColor)
//                .fillMaxWidth()
//        )
    }
}
