package com.valkotova.wishboxwithcompose.data.model.requests

import kotlinx.serialization.Serializable

@Serializable
data class AddWishToListRequest(
    val wishList : Int,
    val wishes: List<Int>?
)