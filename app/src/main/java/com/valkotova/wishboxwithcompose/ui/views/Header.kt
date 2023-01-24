package com.valkotova.wishboxwithcompose.ui.views

import android.widget.Button
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.valkotova.wishboxwithcompose.R
import com.valkotova.wishboxwithcompose.ui.theme.ColorTextCaption
import com.valkotova.wishboxwithcompose.ui.theme.ColorTextGeneral

@Preview
@Composable
fun Header(
    title : String = "title",
    navController: NavHostController? = null,
    hasBack : Boolean = true,
    firstButton : @Composable (() -> Unit)? = null,
    secondButton : @Composable (() -> Unit)? = null
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.background)
            .height(44.dp)
    ) {
        if (hasBack)
            Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = stringResource(R.string.back),
                    modifier = Modifier.clickable{
                        navController?.navigateUp()
                    }.size(44.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                )
        Text(
            text = title,
            style = MaterialTheme.typography.labelLarge,
            color = ColorTextCaption,
            modifier = Modifier.weight(1f)
        )
        firstButton?.invoke()
        secondButton?.invoke()
    }
}