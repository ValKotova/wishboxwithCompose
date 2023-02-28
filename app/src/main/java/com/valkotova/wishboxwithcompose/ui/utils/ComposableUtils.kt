package com.valkotova.wishboxwithcompose.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.valkotova.wishboxwithcompose.R
import com.valkotova.wishboxwithcompose.domain.model.users.Gender

@Composable
fun Gender.getString() = when(this) {
    Gender.female -> stringResource(id = R.string.gender_female)
    Gender.male -> stringResource(id = R.string.gender_male)
    Gender.unknown -> stringResource(id = R.string.gender_unknown)
}