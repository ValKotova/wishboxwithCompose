package com.valkotova.wishboxwithcompose.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.valkotova.wishboxwithcompose.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.roboto_medium)),
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.roboto_medium)),
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    ),
   bodyMedium = TextStyle(
       fontFamily = FontFamily(Font(R.font.roboto_medium)),
       fontWeight = FontWeight.Normal,
       fontSize = 14.sp,
       lineHeight = 22.sp,
       letterSpacing = 0.5.sp
   )
)