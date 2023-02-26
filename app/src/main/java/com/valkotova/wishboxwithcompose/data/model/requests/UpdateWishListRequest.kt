package com.valkotova.wishboxwithcompose.data.model.requests

import kotlinx.serialization.Serializable

@Serializable
data class UpdateWishListRequest(
    val id: Int,
    val name: String? = null,
    val wishes: List<Int>? = null,
    val viewers: List<Int>? = null,
    val visibility: Int? = null,
    val date: Long? = null,
    val preview: Int? = null,
    val favorite: Boolean? = null,
)