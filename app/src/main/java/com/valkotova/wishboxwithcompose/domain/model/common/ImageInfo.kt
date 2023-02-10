package com.valkotova.wishboxwithcompose.domain.model.common

import com.valkotova.wishboxwithcompose.data.model.ImageInfoData
import com.valkotova.wishboxwithcompose.data.model.ImageVersionsData

data class ImageInfo(
    val key: String,
    val versions: ImageVersions
)

data class ImageVersions(
    val big: String,
    val medium: String,
    val small: String
)

fun ImageVersionsData.to_ImageVersions() = ImageVersions(
    big = this.big,
    medium = this.medium,
    small = this.small
)

fun ImageInfoData.to_ImageInfo() = ImageInfo(
    key = this.key,
    versions = this.versions.to_ImageVersions()
)