package com.valkotova.wishboxwithcompose.data.model.requests

import kotlinx.serialization.Serializable

@Serializable
data class DropWishStatusRequest(
    val wishId: Int
)