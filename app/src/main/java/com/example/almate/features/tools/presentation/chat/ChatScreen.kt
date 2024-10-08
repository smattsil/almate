package com.example.almate.features.tools.presentation.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.almate.R
import com.example.almate.features.tools.presentation.components.MessageTextField
import com.example.almate.presentation.theme.proximaNovaFamily

@Composable
fun ChatScreen(
    onBackButtonClick: () -> Unit,
    chatViewModel: ChatViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = { ChatTopAppBar(onBackButtonClick = onBackButtonClick) },
        bottomBar = { ChatBottomAppBar(
            value = chatViewModel.message,
            onValueChange = { chatViewModel.message = it },
            modifier = Modifier
        ) }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatTopAppBar(
    onBackButtonClick: () -> Unit,
) {
    TopAppBar(
        navigationIcon = {
            IconButton(
                onClick = onBackButtonClick
            ) {
                Icon(
                    painter = painterResource(R.drawable.arrow_back_24dp_e8eaed_fill0_wght400_grad0_opsz24),
                    contentDescription = null
                )
            }
        },
        title = {
            Text(
                text = "Chat",
                fontFamily = proximaNovaFamily,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    )
}

@Composable
fun ChatBottomAppBar(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.Bottom,
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(horizontal = 16.dp, vertical = 24.dp)
            .imePadding()
    ) {
        MessageTextField(
            value = value,
            onValueChange = { onValueChange(it) },
            modifier = Modifier.weight(9f)
        )
        Spacer(Modifier.width(8.dp))
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .fillMaxSize()
                .background(Color.White)
                .weight(1f)
        ) {
            Icon(
                painter = painterResource(R.drawable.arrow_upward_24dp_e8eaed_fill0_wght400_grad0_opsz24),
                tint = Color.Black,
                contentDescription = null
            )
        }
    }
}
