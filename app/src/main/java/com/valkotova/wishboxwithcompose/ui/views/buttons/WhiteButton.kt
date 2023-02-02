package com.valkotova.wishboxwithcompose.ui.views.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.valkotova.wishboxwithcompose.R
import com.valkotova.wishboxwithcompose.ui.main.theme.ColorTextGeneral
import com.valkotova.wishboxwithcompose.ui.main.theme.ColorTextGray

@Preview
@Composable
fun WhiteButton(
    modifier : Modifier = Modifier,
    text : String = "White button",
    onClick : () -> Unit = {}
){
    OutlinedButton(
        onClick = onClick,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = ColorTextGeneral,
            containerColor = MaterialTheme.colorScheme.inversePrimary,
            disabledContentColor = ColorTextGray
        ),
        shape = RoundedCornerShape(15.dp),
        modifier = modifier
    ){
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(horizontal = 22.dp, vertical = 5.dp)
        )
    }
}