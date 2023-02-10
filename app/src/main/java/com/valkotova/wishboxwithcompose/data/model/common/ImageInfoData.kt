package com.valkotova.wishboxwithcompose.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ImageInfoData(
    val key: String,
    val versions: ImageVersionsData
)

@Serializable
data class ImageVersionsData(
    val big: String,
    val medium: String,
    val small: String
)