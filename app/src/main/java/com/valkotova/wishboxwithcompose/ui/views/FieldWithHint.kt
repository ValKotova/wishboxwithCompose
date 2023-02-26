package com.valkotova.wishboxwithcompose.ui.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.valkotova.wishboxwithcompose.ui.main.theme.ColorTextGeneral
import com.valkotova.wishboxwithcompose.ui.main.theme.ColorTextGray

@Composable
fun FieldWithHint(
    modifier : Modifier,
    text : String,
    hint : String,
    isLink : Boolean = false
){
    val uriHandler = LocalUriHandler.current
   Column(
       modifier = modifier
   ) {
       Text(
           text = hint,
           style = MaterialTheme.typography.bodySmall,
           textAlign = TextAlign.Left,
           color = ColorTextGray
       )
       Text(
           text = text,
           style = MaterialTheme.typography.bodyLarge,
           textAlign = TextAlign.Left,
           color = ColorTextGeneral,
           modifier = Modifier.clickable {
               if(isLink){
                   uriHandler.openUri(text)
               }
           },
           textDecoration = if(isLink) TextDecoration.Underline else null
       )
   }
}