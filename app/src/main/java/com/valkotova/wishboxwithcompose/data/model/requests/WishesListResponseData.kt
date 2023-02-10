package com.valkotova.wishboxwithcompose.data.model.requests

import com.valkotova.wishboxwithcompose.data.model.Wishes.WishShortInfoData
import kotlinx.serialization.Serializable

@Serializable
data class WishesListResponseData(
    val wishes: List<WishShortInfoData>
)
