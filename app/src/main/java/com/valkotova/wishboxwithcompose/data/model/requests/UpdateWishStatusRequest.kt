package com.valkotova.wishboxwithcompose.data.model.requests

import com.valkotova.wishboxwithcompose.data.model.Wishes.WishStatus
import kotlinx.serialization.Serializable

@Serializable
data class UpdateWishStatusRequest(
    val status: WishStatus,
    val wishId: Int
)