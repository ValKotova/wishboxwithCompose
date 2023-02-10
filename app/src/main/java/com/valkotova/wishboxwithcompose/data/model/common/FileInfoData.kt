package com.valkotova.wishboxwithcompose.data.model.common

import com.valkotova.wishboxwithcompose.data.model.ImageInfoData
import kotlinx.serialization.Serializable

@Serializable
data class FileInfoData(
    val id: Int,
    val fileInfo: ImageInfoData,
    val index: Int?,
)