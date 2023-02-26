package com.valkotova.wishboxwithcompose.data.model.responses

import com.valkotova.wishboxwithcompose.data.model.wishlists.WishListInfoData
import kotlinx.serialization.Serializable

@Serializable
data class AllWishListsResponseData(
    val lists: List<WishListInfoData>
)
