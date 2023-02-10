package com.valkotova.wishboxwithcompose.data.model.requests

import kotlinx.serialization.Serializable

@Serializable
data class CreateWishRequest(
    val name: String,
    val link: String?,
    val price: String?,
    val comment: String?,
    val previews: List<Int>
)