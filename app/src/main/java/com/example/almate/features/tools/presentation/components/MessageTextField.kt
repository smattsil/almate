package com.example.almate.features.tools.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.almate.presentation.theme.cardBackgroundColor
import com.example.almate.presentation.theme.proximaNovaFamily

@Composable
fun MessageTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(cardBackgroundColor)
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 16.dp)
    ) {
        if (value.isEmpty()) Text(
            text = "Message",
            fontFamily = proximaNovaFamily,
            lineHeight = 16.sp,
            fontSize = 16.sp,
            color = Color.White
        )
        BasicTextField(
            value = value,
            onValueChange = { onValueChange(it) },
            cursorBrush = SolidColor(Color.White.copy(alpha = 0.67f)),
            textStyle = TextStyle.Default.copy(
                fontFamily = proximaNovaFamily,
                fontSize = 16.sp,
                color = Color.White
            )
        )
    }
}
