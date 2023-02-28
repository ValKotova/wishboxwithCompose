package com.valkotova.wishboxwithcompose.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.valkotova.wishboxwithcompose.R
import com.valkotova.wishboxwithcompose.domain.model.users.Gender
import com.valkotova.wishboxwithcompose.ui.utils.getString

@Composable
fun GenderSelector(
    curGender : Gender,
    onSelect : (Gender) -> Unit
){
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .padding(start = 30.dp, end = 30.dp, top = 15.dp)
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.inversePrimary,
                RoundedCornerShape(15.dp)
            )
            .padding(3.dp)
    ){
        for(gender in Gender.values()){
            Text(
                text = gender.getString(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelMedium,
                color = if(gender == curGender)
                        MaterialTheme.colorScheme.background
                    else
                        MaterialTheme.colorScheme.secondary,
                modifier = (if(gender == curGender)
                        Modifier.background(
                            MaterialTheme.colorScheme.primary,
                            RoundedCornerShape(15.dp)
                        )
                    else {
                        Modifier
                })
                    .clickable { onSelect.invoke(gender) }
                    .padding(12.dp, 12.dp)

            )
        }
    }
}