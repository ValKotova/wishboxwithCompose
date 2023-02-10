package com.valkotova.wishboxwithcompose.domain.model.common

import com.valkotova.wishboxwithcompose.data.model.common.FileInfoData

data class FileInfo(
    val id: Int,
    val fileInfo: ImageInfo,
    val index: Int?,
)

fun FileInfoData.to_FileInfo() = FileInfo(
    id = this.id,
    fileInfo = this.fileInfo.to_ImageInfo(),
    index = this.index
)