package com.valkotova.wishboxwithcompose.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.StateFlow
import kotlin.time.TimeSource

//@Preview
@Composable
fun WhiteEditText(
    modifier : Modifier = Modifier,
    label : String = "Label",
    hint : String = "hint",
    singleLine : Boolean = true,
    state : State<EditTextState>,
    onValueChange : (String) -> Unit
){
        BasicTextField(
            modifier = modifier,
            value = state.value.text,
            onValueChange = onValueChange,
            singleLine = singleLine,
            textStyle = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.secondary),

            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .background(
                            MaterialTheme.colorScheme.inversePrimary,
                            RoundedCornerShape(15.dp)
                        )
                        .border(
                            width =  2.dp,
                            color = if(state.value.hasError) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.inversePrimary,
                            shape = RoundedCornerShape(15.dp))
                        .padding(22.dp, 14.dp)
                ) {

                    if (state.value.text.isEmpty()) {
                        Text(text = hint, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.secondary)
                    }
                    innerTextField()  //<-- Add this
                }
            }
        )
}

data class EditTextState(
    val text : String,
    val hasError : Boolean
){
    companion object{
        val EMPTY_STATE = EditTextState("", false)
    }
}