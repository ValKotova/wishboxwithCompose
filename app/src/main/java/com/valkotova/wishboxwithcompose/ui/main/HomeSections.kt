package com.valkotova.wishboxwithcompose.ui.main

import androidx.annotation.DrawableRes
import com.valkotova.wishboxwithcompose.R

enum class HomeSections(
    @DrawableRes
    val icon: Int,
    val route: String
) {
    LISTS(R.drawable.ic_bottom_bar_1, Destinations.LISTS),
    CALENDAR(R.drawable.ic_bottom_bar_2, Destinations.CALENDAR),
    FRIENDS(R.drawable.ic_bottom_bar_3, Destinations.FRIENDS),
    MENU(R.drawable.ic_bottom_bar_4, Destinations.MENU)
}