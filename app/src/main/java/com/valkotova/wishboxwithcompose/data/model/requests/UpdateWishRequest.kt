package com.valkotova.wishboxwithcompose.data.model.requests

import kotlinx.serialization.Serializable

@Serializable
data class UpdateWishRequest(
    val id: Int,
    val name: String? = null,
    val link: String? = null,
    val price: String? = null,
    val comment: String? = null,
    val favorite: Boolean? = null,
    val previews: List<Int>? = null
)
