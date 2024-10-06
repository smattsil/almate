package com.example.almate.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.almate.R

val proximaNovaFamily = FontFamily(
    Font(R.font.proximanova_thin, FontWeight.Thin),
    Font(R.font.proximanova_light, FontWeight.Light),
    Font(R.font.proximanova_regular, FontWeight.Normal),
    Font(R.font.proximanova_semibold, FontWeight.SemiBold),
    Font(R.font.proximanova_bold, FontWeight.Bold),
    Font(R.font.proximanova_extrabold, FontWeight.ExtraBold),
    Font(R.font.proximanova_black, FontWeight.Black),
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = proximaNovaFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)