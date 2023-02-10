package com.valkotova.wishboxwithcompose.domain.model.common

import com.valkotova.wishboxwithcompose.data.model.common.MediaInfoData

data class MediaInfo(
    val id: Int,
    val imageInfo: ImageInfo,
    val index: Int?,
) {
    fun toFileInfo(): FileInfo {
        return FileInfo(
            id = id,
            fileInfo = imageInfo,
            index = index
        )
    }
}

enum class MediaType {
    AVATAR, WISH, WISH_LIST
}

fun MediaInfoData.to_MediaInfo() = MediaInfo(
    id = this.id, imageInfo = this.imageInfo.to_ImageInfo(), index = this.index
)