package com.example.almate.features.home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.almate.R
import com.example.almate.presentation.theme.proximaNovaFamily

@Composable
fun SubjectPlaceholderScreen(
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .alpha(0.67f)
    ) {
        Icon(
            painter = painterResource(R.drawable.school_24dp_e8eaed_fill1_wght400_grad0_opsz24),
            contentDescription = null,
            modifier = Modifier.size(144.dp)
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = "Select a subject",
            fontSize = MaterialTheme.typography.headlineLarge.fontSize,
            lineHeight = MaterialTheme.typography.headlineLarge.lineHeight,
            fontFamily = proximaNovaFamily,
            fontWeight = FontWeight.SemiBold

        )
    }
}