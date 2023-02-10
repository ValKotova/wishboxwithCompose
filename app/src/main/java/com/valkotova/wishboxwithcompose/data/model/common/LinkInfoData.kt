package com.valkotova.wishboxwithcompose.data.model.common

import kotlinx.serialization.Serializable

@Serializable
data class LinkInfoData(
    val name: String,
    val link: String
)