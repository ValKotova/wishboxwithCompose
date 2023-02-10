package com.valkotova.wishboxwithcompose.data.model.Wishes

import com.valkotova.wishboxwithcompose.data.model.common.FileInfoData
import com.valkotova.wishboxwithcompose.data.model.users.UserInfoData
import kotlinx.serialization.Serializable

@Serializable
data class WishInfoData(
    val id: Int,
    val name: String,
    val previews: List<FileInfoData>,
    val booker: UserInfoData?,
    val comment: String?,
    val favorite: Boolean,
    val link: String?,
    val list: Int?,
    val status: WishStatus,
    val price: Double?,
    val user: Int
)
