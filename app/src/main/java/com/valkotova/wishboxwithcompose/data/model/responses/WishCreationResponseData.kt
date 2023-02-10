package com.valkotova.wishboxwithcompose.data.model.responses


import kotlinx.serialization.Serializable

@Serializable
data class WishCreationResponseData(
    val wishId : Int
)