package com.valkotova.wishboxwithcompose.data.model.Wishes

import com.valkotova.wishboxwithcompose.data.model.common.FileInfoData
import kotlinx.serialization.Serializable

@Serializable
data class WishShortInfoData(
    val id: Int,
    val name: String,
    val preview: FileInfoData?,
    val status: WishStatus,
    val favorite : Boolean,
    val booker : Int?
)