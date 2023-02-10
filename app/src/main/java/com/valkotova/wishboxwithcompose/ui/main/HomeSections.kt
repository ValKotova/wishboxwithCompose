package com.valkotova.wishboxwithcompose.ui.main

import android.util.Config.PROFILE
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import com.valkotova.wishboxwithcompose.R

enum class HomeSections(
    @DrawableRes
    val icon: Int,
    val route: String
) {
    LISTS(R.drawable.ic_bottom_bar_1, MainDestinations.LISTS),
    CALENDAR(R.drawable.ic_bottom_bar_2, MainDestinations.CALENDAR),
    FRIENDS(R.drawable.ic_bottom_bar_3, MainDestinations.FRIENDS),
    MENU(R.drawable.ic_bottom_bar_4, MainDestinations.MENU)
}