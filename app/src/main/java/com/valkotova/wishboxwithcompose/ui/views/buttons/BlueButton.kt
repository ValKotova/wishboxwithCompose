package com.valkotova.wishboxwithcompose.ui.views.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.valkotova.wishboxwithcompose.ui.theme.ColorTextGeneral
import com.valkotova.wishboxwithcompose.ui.theme.ColorTextGray

@Preview
@Composable
fun BlueButton(
    modifier : Modifier = Modifier,
    text : String = "Blue button",
    onClick : () -> Unit = {}
){
    OutlinedButton(
        onClick = onClick,
        border = BorderStroke(0.dp, MaterialTheme.colorScheme.primary),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.background,
            containerColor = MaterialTheme.colorScheme.primary,
            disabledContentColor = ColorTextGray
        ),
        shape = RoundedCornerShape(15.dp),
        modifier = modifier
    ){
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}