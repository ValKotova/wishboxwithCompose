package com.valkotova.wishboxwithcompose.data.model.common

import com.valkotova.wishboxwithcompose.data.model.ImageInfoData
import kotlinx.serialization.Serializable

@Serializable
data class MediaInfoData(
    val id: Int,
    val imageInfo: ImageInfoData,
    val index: Int?,
){
    fun toFileInfo(): FileInfoData {
        return FileInfoData(
            id = id,
            fileInfo = imageInfo,
            index = index
        )
    }
}