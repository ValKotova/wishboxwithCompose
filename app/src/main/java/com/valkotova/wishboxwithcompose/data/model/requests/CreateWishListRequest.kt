package com.valkotova.wishboxwithcompose.data.model.requests

import kotlinx.serialization.Serializable

@Serializable
data class CreateWishListRequest(
    val name: String,
    val wishes: List<Int>,
    val viewers: List<Int>,
    val visibility: Int,
    val date: Long?,
    val preview: Int?,
)