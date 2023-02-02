package com.valkotova.wishboxwithcompose.ui.views.navBar

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun BottomNavIndicator(
    strokeWidth: Dp = 2.dp,
    color: Color = Color.Black,
    shape: Shape = RoundedCornerShape(15.dp)
) {
    Spacer(
        modifier = Modifier
            .fillMaxSize()
            .border(strokeWidth, color, shape)
    )
}